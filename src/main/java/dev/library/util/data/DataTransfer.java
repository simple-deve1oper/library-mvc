package dev.library.util.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.library.form.BookForm;
import dev.library.form.PersonForm;
import dev.library.form.ReportForm;
import dev.library.model.component.AdditionalData;
import dev.library.model.component.BasicData;
import dev.library.service.AuthorService;
import dev.library.service.BookService;
import dev.library.service.PersonService;
import dev.library.service.PublisherService;
import dev.library.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс для перевода данных из модели в форму и наоборот
 * @version 1.0
 */
@Component
public class DataTransfer {
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private PublisherService publisherService;
	@Autowired
	private PersonService personService;

	/**
	 * Перевод данных из объекта типа PersonForm в объект типа Person
	 * @param personForm - объект типа PersonForm
	 * @return объект типа Person
	 */
	public Person transferDataPersonFromFormToModel(PersonForm personForm) {
		System.out.println("Until transfer: " + personForm);
		
		String lastName = personForm.getLastName();
		String firstName = personForm.getFirstName();
		String middleName = personForm.getMiddleName();
		String birthDateToString = personForm.getBirthDate();
		LocalDate birtDate = LocalDate.parse(birthDateToString);
		String email = personForm.getEmail();
		String phoneNumber = personForm.getPhoneNumber();
		phoneNumber = phoneNumber.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
		
		BasicData basicData;
		if (middleName.isBlank()) {
			basicData = new BasicData(lastName, firstName, birtDate);
		} else {
			basicData = new BasicData(lastName, firstName, middleName, birtDate);
		}
		
		System.out.println("email from form: " + email);
		System.out.println("email is blank: " + email.isBlank());
		AdditionalData additionalData;
		if (email.isBlank()) {
			additionalData = new AdditionalData(phoneNumber);
		} else {
			additionalData = new AdditionalData(email, phoneNumber);
		}
		
		Person person = new Person(basicData, additionalData);
		System.out.println("After transfer: " + person);
		return person;
	}

	/**
	 * Перевод данных из объекта типа Person в объект типа PersonForm
	 * @param person - объект типа Person
	 * @return объект типа PersonForm
	 */
	public PersonForm transferDataPersonFromModelToForm(Person person) {
		String lastName = person.getBasicData().getLastName();
		String firstName = person.getBasicData().getFirstName();
		String middleName = person.getBasicData().getMiddleName();
		if (middleName == null) {
			middleName = "";
		}
		String birthDate = person.getBasicData().getBirthDate().toString();
		String email = person.getAdditionalData().getEmail();
		if (email == null) {
			email = "";
		}
		String phoneNumber = person.getAdditionalData().getPhoneNumber();
		
		PersonForm personForm = new PersonForm(lastName, firstName, middleName, birthDate, email, phoneNumber);
		return personForm;
	}

	/**
	 * Перевод данных из объекта типа BookForm в объект типа Book
	 * @param bookForm - объект типа BookForm
	 * @return объект типа Book
	 */
	public Book transferDataBookFromFormToModel(BookForm bookForm) {
		String name = bookForm.getName();
		long authorId = bookForm.getAuthorId();
		Author author = authorService.getAuthor(authorId).get();
		long publisherId = bookForm.getPublisherId();
		Publisher publisher = publisherService.getPublisher(publisherId).get();
		String publicationYear = bookForm.getPublicationYear();

		Book book = new Book(name, author, publisher, publicationYear, true);
		return book;
	}

	/**
	 * Перевод данных из объекта типа Book в объект типа BookForm
	 * @param book - объект типа Book
	 * @return объект типа BookForm
	 */
	public BookForm transferDataBookFromModelToForm(Book book) {
		String name = book.getName();
		long authorId  = book.getAuthor().getId();
		long publisherId = book.getPublisher().getId();
		String publicationYear = book.getPublicationYear();

		BookForm bookForm = new BookForm(name, authorId, publisherId, publicationYear);
		return bookForm;
	}

	/**
	 * Перевод данных из объекта типа ReportForm в объект типа Report
	 * @param reportForm - объект типа ReportForm
	 * @return объект типа Report
	 */
	public Report transferDataReportFromFormToModel(ReportForm reportForm) {
		long personId = reportForm.getPersonId();
		Person person = personService.getPerson(personId).get();
		long bookId = reportForm.getBookId();
		Book book = bookService.getBook(bookId).get();
		LocalDateTime issueDate = LocalDateTime.now();

		Report report = new Report();
		report.setPerson(person);
		report.setBook(book);
		report.setIssueDate(issueDate);
		return report;
	}

	/**
	 * Перевод данных из объекта типа Report в объект типа ReportForm
	 * @param report - объект типа Report
	 * @return объект типа ReportForm
	 */
	public ReportForm transferDataReportFromModelToForm(Report report) {
		long personId = report.getPerson().getId();
		long bookId  = report.getBook().getId();
		LocalDateTime issueDate = report.getIssueDate();
		LocalDateTime returnDate = report.getReturnDate();

		ReportForm reportForm = new ReportForm(personId, bookId, issueDate, returnDate);
		return reportForm;
	}
}
