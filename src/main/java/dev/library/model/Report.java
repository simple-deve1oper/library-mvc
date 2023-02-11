package dev.library.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Класс для описания информации об отчёте
 * @version 1.0
 */
public class Report implements Cloneable {
	/* идентификатор */
	private long id;
	/* читатель */
	private Person person;
	/* книга */
	private Book book;
	/* дата выдачи */
	private LocalDateTime issueDate;
	/* дата возвращения книги */
	private LocalDateTime returnDate;

	/**
	 * Конструктор по умолчанию для создания нового объекта типа Report
	 * @param id - идентификатор
	 * @param person - человек
	 * @param book - книга
	 * @param issueDate - дата выдачи
	 * @param returnDate - дата возврата
	 * @see #Report(long, Person, Book, LocalDateTime)
	 */
	public Report(long id, Person person, Book book, LocalDateTime issueDate, LocalDateTime returnDate) {
		this(id, person, book, issueDate);
		this.returnDate = returnDate;
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа Report
	 * @param id - идентификатор
	 * @param person - человек
	 * @param book - книга
	 * @param issueDate - дата выдачи
	 */
	public Report(long id, Person person, Book book, LocalDateTime issueDate) {
		this.id = id;
		this.person = person;
		this.book = book;
		this.issueDate = issueDate;
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа Report
	 */
	public Report() {}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LocalDateTime getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}
	public LocalDateTime getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public Report clone() throws CloneNotSupportedException {
		Report report = (Report) super.clone();
		report.person = person.clone();
		report.book = book.clone();
		return report;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Report report = (Report) obj;
		return (id == report.id) && (person == report.person || (person != null && person.equals(report.person))) &&
				(book == report.book || (book != null && book.equals(report.book))) &&
				(issueDate == report.issueDate || (issueDate != null && issueDate.equals(report.issueDate))) &&
				(returnDate == report.returnDate || (returnDate != null && returnDate.equals(report.returnDate)));
	}

	@Override
	public int hashCode() {
		final int CODE = (int)(31 * 1 + id + (person == null ? 0 : person.hashCode()) +
				(book == null ? 0 : book.hashCode()) + (issueDate == null ? 0 : issueDate.hashCode()) +
				(returnDate == null ? 0 : returnDate.hashCode()));
		return CODE;
	}

	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
		String objectToString = joiner.add("id=" + id).add("person=" + person).add("book=" + book)
				.add("issueDate=" + issueDate).add("returnDate=" + returnDate).toString();
		return objectToString;
	}
}
