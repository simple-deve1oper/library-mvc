package dev.library.controller;

import dev.library.form.BookForm;
import dev.library.form.ReportForm;
import dev.library.model.*;
import dev.library.service.*;
import dev.library.util.PivotTable;
import dev.library.util.data.DataTransfer;
import dev.library.util.file.FileProcessing;
import dev.library.util.file.RegexProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@PropertySource("classpath:general.properties")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private PersonService personService;
    @Autowired
    private DataTransfer dataTransfer;
    @Value("${file.pathToReport}")
    private String pathToReport;
    private String errorMessage;

    /**
     * Получение страницы со списком всех книг
     */
    @GetMapping("/all")
    public String showAllReaders(Model model) {
        if (errorMessage != null) {
            String message = errorMessage;
            errorMessage = null;
            model.addAttribute("errorMessage", message);
        } else {
            List<Book> books = bookService.getAllBooks();
            model.addAttribute("books", books);
        }
        return "books/showAllBooks";
    }

    /**
     * Получение страницы с формой добавления книги
     */
    @GetMapping("/add")
    public String addBook(@ModelAttribute("bookForm") BookForm bookForm, Model model) {
        List<Author> authors = authorService.readAllAuthors();
        List<Publisher> publishers = publisherService.readAllPublishers();
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);

        return "books/addBook";
    }

    /**
     * Добавление книги в базу данных
     */
    @PostMapping("/add")
    public String createReader(@ModelAttribute("bookForm") @Valid BookForm bookForm,
                               BindingResult bindingResult, Model model) {
        System.out.println(bookForm);
        Book book = dataTransfer.transferDataBookFromFormToModel(bookForm);

        List<Author> authors = authorService.readAllAuthors();
        List<Publisher> publishers = publisherService.readAllPublishers();
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);

        if (bindingResult.hasErrors()) {

            return "books/addBook";
        }

        if (checkBookForAvailabilityInDatabase(book)) {
            model.addAttribute("duplicate", "Книга с такими данными уже существует");

            return "books/addBook";
        }

        bookService.addBook(book);

        return "redirect:/books/all";
    }

    /**
     * Получение страницы с информацией о книге
     */
    @GetMapping("/info/{id}")
    public String showReader(@PathVariable(name = "id") long id, Model model) {
        Optional<Book> optionalBook = bookService.getBook(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            model.addAttribute("book", book);
            if (book.isAvailable()) {
                ReportForm reportForm = new ReportForm();
                model.addAttribute("reportForm", reportForm);

                List<Person> people = personService.getAllPeople();
                model.addAttribute("people", people);
            } else {
                Report report = reportService.findIssuedBook(book).get();
                long reportId = report.getId();
                Person person = report.getPerson();
                model.addAttribute("reportId", reportId);
                model.addAttribute("person", person);
            }
        } else {
            model.addAttribute("errorMessage", "Произошла ошибка загрузки данных о книге");
        }

        return "books/informationAboutBook";
    }

    /**
     * Получение страницы с формой изменения книги
     */
    @GetMapping("/edit/{id}")
    public String updateData(@PathVariable("id") long id, Model model) {
        Optional<Book> optionalBook = bookService.getBook(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookForm bookForm = dataTransfer.transferDataBookFromModelToForm(book);
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("id", id);
            List<Author> authors = authorService.readAllAuthors();
            List<Publisher> publishers = publisherService.readAllPublishers();
            model.addAttribute("authors", authors);
            model.addAttribute("publishers", publishers);
        } else {
            model.addAttribute("errorMessage", "Произошла ошибка загрузки данных о книге");
        }

        return "books/editBookData";
    }

    /**
     * Изменение книги в базе данных
     */
    @PatchMapping("/edit/{id}")
    public String saveUpdatedData(@ModelAttribute("bookForm") @Valid BookForm bookForm,
                                  BindingResult bindingResult, Model model, @PathVariable("id") long id) {
        Book book = dataTransfer.transferDataBookFromFormToModel(bookForm);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("id", id);

            return "books/editBookData";
        }

        Optional<Book> optionalBook = bookService.getBook(id);
        Book bookFromDB = optionalBook.get();
        if (book.getName().equals(bookFromDB.getName()) && book.getAuthor().equals(bookFromDB.getAuthor()) &&
                book.getPublisher().equals(bookFromDB.getPublisher()) &&
                book.getPublicationYear().equals(bookFromDB.getPublicationYear())) {
            return "redirect:/books/info/" + id;
        } else if (!book.getName().equals(bookFromDB.getName()) && book.getAuthor().equals(bookFromDB.getAuthor()) &&
                book.getPublisher().equals(bookFromDB.getPublisher()) &&
                book.getPublicationYear().equals(bookFromDB.getPublicationYear())) {
            if (checkBookForAvailabilityInDatabase(book)) {
                model.addAttribute("duplicate", "Книга с такими данными уже существует");
                model.addAttribute("bookForm", bookForm);
                model.addAttribute("id", id);
                List<Author> authors = authorService.readAllAuthors();
                List<Publisher> publishers = publisherService.readAllPublishers();
                model.addAttribute("authors", authors);
                model.addAttribute("publishers", publishers);

                return "books/editBookData";
            }
        }

        boolean result = bookService.updateInfo(book, id);
        if (!result) {
            model.addAttribute("errorMessage", "Произошла ошибка обновления данных");
            return "books/editBookData";
        }

        return "redirect:/books/info/" + id;
    }

    /**
     * Выдача книги читателю
     */
    @PostMapping("/{bookId}/issue")
    public String issueBook(@PathVariable("bookId") long bookId, @ModelAttribute("reportForm") ReportForm reportForm) {
        Report report = dataTransfer.transferDataReportFromFormToModel(reportForm);
        Book book = report.getBook();
        book.setAvailable(false);
        reportService.addReport(report);
        bookService.updateInfo(book, bookId);

        return "redirect:/books/info/" + bookId;
    }

    /**
     * Получение книги обратно от читателя
     */
    @PatchMapping("/{bookId}/available/{reportId}")
    public String returnBook(@PathVariable("bookId") long bookId,
                             @PathVariable("reportId") long reportId) {
        Optional<Book> optionalBook = bookService.getBook(bookId);
        Book book = optionalBook.get();
        Report report = reportService.findIssuedBook(book).get();
        report.setReturnDate(LocalDateTime.now());
        book.setAvailable(true);
        reportService.updateInfo(report, reportId);
        bookService.updateInfo(book, bookId);

        return "redirect:/books/info/" + bookId;
    }

    /**
     * Удаление книги
     */
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        boolean result = bookService.deleteBook(id);
        if (!result) {
            errorMessage = "Ошибка удаления книги с идентификатором " + id;
        }
        return "redirect:/books/all";
    }

    /**
     * Раздача (выгрузка) файла клиенту
     */
    @GetMapping(value = "/report")
    @ResponseBody
    public void createPivotTable(HttpServletResponse response) {
        try {
            List<Report> reports = reportService.getAllReports();
            final PivotTable pivotTable = new PivotTable(pathToReport);
            String pathToFile = pivotTable.createReport(reports);
            String fileName = RegexProcessing.getArrayAfterSplit("/", pathToFile)[1];

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Transfer-Encoding", "binary");

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fileInputStream = new FileInputStream(pathToFile);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fileInputStream.read(buf)) > 0) {
                bufferedOutputStream.write(buf, 0, len);
            }
            bufferedOutputStream.close();
            response.flushBuffer();
            fileInputStream.close();
            FileProcessing.deleteFile(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка на существование книги с такими же данными (наименование, автор, издательство и год издания)
     * @param book - книга
     * @return результат наличия книги с такими же данными в БД
     */
    public boolean checkBookForAvailabilityInDatabase(Book book) {
        List<Book> books = bookService.getAllBooks();
        Optional<Book> optionalBook = books.stream().filter(b -> b.getName().equals(book.getName()) &&
                b.getAuthor().equals(book.getAuthor()) && b.getPublisher().equals(book.getPublisher()) &&
                b.getPublicationYear().equals(book.getPublicationYear())).findAny();
        return optionalBook.isPresent();
    }
}
