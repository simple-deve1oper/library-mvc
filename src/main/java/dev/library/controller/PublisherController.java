package dev.library.controller;

import dev.library.model.Book;
import dev.library.model.Publisher;
import dev.library.service.BookService;
import dev.library.service.PublisherService;
import dev.library.util.collection.CheckingPublishersCollection;
import dev.library.util.data.ListPublishers;
import dev.library.util.data.parse.JsonParse;
import dev.library.util.file.FileProcessing;
import dev.library.util.file.RegexProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/publishers")
@PropertySource("classpath:general.properties")
public class PublisherController {
    @Autowired
    private PublisherService publisherService;
    @Autowired
    private BookService bookService;
    @Value("${file.directory}")
    private String directory;

    /* Карта для хранения результата из другого метода */
    private Map<String, List<Publisher>> publishersMap;
    /* Строковая переменная для хранения сообщения из другого метода */
    private String errorMessage;

    /**
     * Получение страницы со списком всех издательств
     */
    @GetMapping("/all")
    public String showAllPublishers(Model model) {
        publishersMap = null;

        if (errorMessage != null) {
            String message = errorMessage;
            model.addAttribute("errorMessage", message);
            errorMessage = null;
        }

        List<Publisher> publishers = publisherService.readAllPublishers();
        List<Book> books = bookService.getAllBooks();
        Map<String, List<Publisher>> map = CheckingPublishersCollection.checkPublishersFromDb(publishers, books);
        List<Publisher> permit = map.get("permit");
        List<Publisher> restrict = map.get("restrict");
        model.addAttribute("permit", permit);
        model.addAttribute("restrict", restrict);

        return "publishers/showAllPublishers";
    }

    /**
     * Загрузка переданного файла
     */
    @PostMapping("/upload")
    public String uploadJsonFileWithPublishers(@RequestParam("file") MultipartFile file) {
        FileProcessing.createDirectory(directory);

        String filename = file.getOriginalFilename();
        String pathToFile = directory + "/" + filename;

        while (FileProcessing.isExists(pathToFile)) {
            pathToFile = RegexProcessing.renameFileCount(pathToFile, directory);
        }

        try {
            FileProcessing.saveFile(file, pathToFile);
            Optional<ListPublishers> optionalPublishers = JsonParse.parsePublishersFromJSON(pathToFile);
            if (optionalPublishers.isPresent()) {
                ListPublishers listAuthors = optionalPublishers.get();
                List<Publisher> publishers = listAuthors.publishers();
                List<Publisher> publishersFromDb = publisherService.readAllPublishers();
                publishersMap = CheckingPublishersCollection.checkPublishersFromJsonFile(publishers, publishersFromDb);
                List<Publisher> ok = publishersMap.get("ok");
                if (!ok.isEmpty()) {
                    publisherService.addAuthorsFromJson(ok);
                }
            } else {
                errorMessage = "Ошибка парсинга JSON-файла";
            }
        } finally {
            FileProcessing.deleteFile(pathToFile);
        }

        return "redirect:/publishers/upload/info";
    }

    /**
     * Получение страницы с информацией о загруженном файле
     */
    @GetMapping("/upload/info")
    public String informationAboutAuthorsFromJsonFile(Model model) {
        if (publishersMap == null) {
            if (errorMessage != null) {
                String message = errorMessage;
                model.addAttribute("errorMessage", message);
            } else {
                model.addAttribute("errorMessage", "Ошибка запроса");
            }
        } else {
            List<Publisher> ok = publishersMap.get("ok");
            List<Publisher> notOk = publishersMap.get("notOk");
            model.addAttribute("ok", ok);
            model.addAttribute("notOk", notOk);
        }

        return "publishers/informationAboutUploadedPublishers";
    }

    /**
     * Удаление издательства по идентификатору
     */
    @DeleteMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id) {
        boolean result = publisherService.deletePublisher(id);
        if (!result) {
            errorMessage = String.format("Ошибка удаления, автора с идентификатором %d не существует", id);
        }
        return "redirect:/publishers/all";
    }
}
