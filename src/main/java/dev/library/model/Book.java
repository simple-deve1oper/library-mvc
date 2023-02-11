package dev.library.model;

import java.util.StringJoiner;

/**
 * Класс для описания информации о книге
 * @version 1.0
 */
public class Book implements Cloneable {
	/* идентификатор */
	private long id;
	/* наименование */
	private String name;
	/* автор */
	private Author author;
	/* издательство */
	private Publisher publisher;
	/* год издания */
	private String publicationYear;
	/* доступность */
	private boolean available;

	/**
	 * Конструктор для создания нового объекта типа Book
	 * @param id - идентификатор
	 * @param name - наименование
	 * @param author - автор
	 * @param publisher - издательство
	 * @param publicationYear - год издания
	 * @param available - доступность
	 */
	public Book(long id, String name, Author author, Publisher publisher, String publicationYear, boolean available) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
		this.available = available;
	}
	/**
	 * Конструктор для создания нового объекта типа Book
	 * @param name - наименование
	 * @param author - автор
	 * @param publisher - издательство
	 * @param publicationYear - год издания
	 * @param available - доступность
	 * @see #Book(long, String, Author, Publisher, String, boolean)
	 */
	public Book(String name, Author author, Publisher publisher, String publicationYear, boolean available) {
		this(0, name, author, publisher, publicationYear, available);
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа Book
	 */
	public Book() {}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public String getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(String publicationYear) {
		this.publicationYear = publicationYear;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public Book clone() throws CloneNotSupportedException {
		Book book = (Book) super.clone();
		book.author = author.clone();
		book.publisher = publisher.clone();
		return book;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Book book = (Book) obj;
		return (id == book.id) && (name == book.name || (name != null && name.equals(book.name))) &&
				(author == book.author || (author != null && author.equals(book.author))) &&
				(publisher == book.publisher || (publisher != null && publisher.equals(book.publisher))) &&
				(publicationYear == book.publicationYear || (publicationYear != null && publicationYear.equals(book.publicationYear)))
				&& (available == book.available);
	}

	@Override
	public int hashCode() {
		final int CODE = (int)(31 * 1 + id + (name == null ? 0 : name.hashCode()) +
				(author == null ? 0 : author.hashCode()) + (publisher == null ? 0 : publisher.hashCode()) +
				(publicationYear == null ? 0 : publicationYear.hashCode()) + (available ? 1 : 0));
		return CODE;
	}

	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
		String objectToString = joiner.add("id=" + id).add("name=" + name).add("author=" + author)
				.add("publisher=" + publisher).add("publicationYear=" + publicationYear)
				.add("available=" + available).toString();
		return objectToString;
	}
}
