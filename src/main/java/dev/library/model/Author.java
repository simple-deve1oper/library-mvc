package dev.library.model;

import java.time.LocalDate;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс для описания информации об авторе
 * @version 1.0
 */
public class Author implements Cloneable {
	/* идентификатор */
	private long id;
	/* фамилия */
	private String lastName;
	/* имя */
	private String firstName;
	/* отчество */
	private String middleName;
	/* дата рождения */
	private LocalDate birthDate;

	/**
	 * Конструктор для создания нового объекта типа Author
	 * @param id - идентификатор
	 * @param lastName - фамилия
	 * @param firstName - имя
	 * @param middleName - отчество
	 * @param birthDate - дата рождения
	 */
	@JsonCreator
	public Author(
			@JsonProperty("id") long id,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("firstName") String firstName,
			@JsonProperty("middleName") String middleName,
			@JsonProperty("birthDate") LocalDate birthDate
	) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.birthDate = birthDate;
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа Author
	 */
	public Author() {}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	@Override
	public Author clone() throws CloneNotSupportedException {
		Author author = (Author) super.clone();
		return author;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Author author = (Author) obj;
        return (id == author.id) &&
                (lastName == author.lastName || (lastName != null && lastName.equals(author.lastName))) && 
                (firstName == author.firstName || (firstName != null && firstName.equals(author.firstName))) &&
                (middleName == author.lastName || (middleName != null && middleName.equals(author.middleName))) &&
                (birthDate == author.birthDate || (birthDate != null && birthDate.equals(author.birthDate)));
    }
	
	@Override
    public int hashCode() {
        final int CODE = (int)(31 * 1 + id + (lastName == null ? 0 : lastName.hashCode()) + 
        		(firstName == null ? 0 : firstName.hashCode()) + (middleName == null ? 0 : middleName.hashCode()) + 
        		(birthDate == null ? 0 : birthDate.hashCode()));
        return CODE;
        		
	}
	
	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("id=" + id).add("lastName=" + lastName).add("firstName=" + firstName)
        		.add("middleName=" + middleName).add("birthDate=" + birthDate).toString();
        return objectToString;
	}
}