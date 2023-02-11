package dev.library.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import dev.library.model.Book;
import dev.library.model.Report;
import dev.library.service.BookService;
import dev.library.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.library.form.PersonForm;
import dev.library.model.Person;
import dev.library.service.PersonService;
import dev.library.util.data.DataTransfer;
import dev.library.util.validator.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
	@Autowired
	private BookService bookService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private PersonValidator personValidator;
	@Autowired
	private DataTransfer dataTransfer;

	/**
	 * Получение страницы со списком всех читателей
	 */
	@GetMapping("/all")
	public String showAllReaders(Model model) {
		List<Person> people = personService.getAllPeople();
		model.addAttribute("people", people);
		return "people/showAllPeople";
	}

	/**
	 * Получение страницы с формой добавления читателя
	 */
	@GetMapping("/registration")
	public String readerRegistration(@ModelAttribute("personForm") PersonForm personForm) {
		return "people/addPerson";
	}

	/**
	 * Добавление читателя в базу данных
	 */
	@PostMapping("/registration")
	public String createReader(@ModelAttribute("personForm") @Valid PersonForm personForm, 
			BindingResult bindingResult, Model model) {
		Person person = dataTransfer.transferDataPersonFromFormToModel(personForm);
		personValidator.validate(person, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "people/addPerson";
		}
		
		personService.personRegistration(person);
		
		return "redirect:/people/all";
	}

	/**
	 * Получение страницы с информацией о читателе
	 */
	@GetMapping("/info/{id}")
	public String showReader(@PathVariable(name = "id") long id, Model model) {
		Optional<Person> optionalPerson = personService.getPerson(id);
		if (optionalPerson.isPresent()) {
			Person person = optionalPerson.get();
			model.addAttribute("person", person);
			List<Report> reports = reportService.findIssuedBooks(person);
			List<Book> books = reports.stream().map(r -> r.getBook()).toList();
			model.addAttribute("books", books);
		} else {
			model.addAttribute("errorMessage", "Произошла ошибка загрузки данных о читателе");
		}
		
		return "people/informationAboutPerson";
	}

	/**
	 * Получение страницы с формой изменения читателя
	 */
	@GetMapping("/edit/{id}")
	public String updateData(@PathVariable("id") long id, Model model) {
		Optional<Person> optionalPerson = personService.getPerson(id);
		if (optionalPerson.isPresent()) {
			Person person = optionalPerson.get();
			PersonForm personForm = dataTransfer.transferDataPersonFromModelToForm(person);
			model.addAttribute("personForm", personForm);
			model.addAttribute("id", id);
		} else {
			model.addAttribute("errorMessage", "Произошла ошибка загрузки данных о читателе");
		}
		
		return "people/editPersonData";
	}

	/**
	 * Изменение читателя в базе данных
	 */
	@PatchMapping("/edit/{id}")
	public String saveUpdatedData(@ModelAttribute("personForm") @Valid PersonForm personForm, 
			BindingResult bindingResult, Model model, @PathVariable("id") long id) {
		Person person = dataTransfer.transferDataPersonFromFormToModel(personForm);
		
		String phoneNumberPersonFromDB = personService.getPerson(id).get().getAdditionalData().getPhoneNumber();
		String phoneNumberPersonFromForm = person.getAdditionalData().getPhoneNumber();
		if (!phoneNumberPersonFromDB.equals(phoneNumberPersonFromForm)) {
			personValidator.validate(person, bindingResult);
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("personForm", personForm);
			model.addAttribute("id", id);
			
			return "people/editReaderData";
		}
		
		boolean result = personService.updateInfo(person, id);
		if (!result) {
			model.addAttribute("errorMessage", "Произошла ошибка обновления данных");
			return "people/editPersonData";
		}
		
		return "redirect:/people/info/" + id;
	}

	/**
	 * Возвращение книги в библиотеку
	 */
	@PatchMapping("/{personId}/book/{bookId}/return")
	public String returnBook(@PathVariable("personId") long personId,
							 @PathVariable("bookId") long bookId,
							 Model model) {
		Book book = bookService.getBook(bookId).get();
		Report report = reportService.findIssuedBook(book).get();
		report.setReturnDate(LocalDateTime.now());
		reportService.updateInfo(report, report.getId());
		book.setAvailable(true);
		bookService.updateInfo(book, bookId);

		return "redirect:/people/info/" + personId;
	}
}
