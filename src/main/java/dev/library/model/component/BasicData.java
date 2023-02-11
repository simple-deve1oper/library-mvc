package dev.library.model.component;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * Класс для описания основной информации о человеке
 * @version 1.0
 */
public class BasicData implements Cloneable {
	/* фамилия */
	private String lastName;
	/* имя */
	private String firstName;
	/* отчество */
	private String middleName;
	/* дата рождения */
	private LocalDate birthDate;

	/**
	 * Конструктор по умолчанию для создания нового объекта типа BasicData
	 */
	public BasicData() {}
	/**
	 * Конструктор для создания нового объекта типа BasicData
	 * @param lastName - фамилия
	 * @param firstName - имя
	 * @param middleName - отчество
	 * @param birthDate - дата рождения
	 * @see #BasicData(String, String, LocalDate)
	 */
	public BasicData(String lastName, String firstName, String middleName, LocalDate birthDate) {
		this(lastName, firstName, birthDate);
		this.middleName = middleName;
	}
	/**
	 * Конструктор для создания нового объекта типа BasicData
	 * @param lastName - фамилия
	 * @param firstName - имя
	 * @param birthDate - дата рождения
	 */
	public BasicData(String lastName, String firstName, LocalDate birthDate) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
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
	public BasicData clone() throws CloneNotSupportedException {
		BasicData data = (BasicData) super.clone();
		return data;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BasicData data = (BasicData) obj;
        return (lastName == data.lastName || (lastName != null && lastName.equals(data.lastName))) && 
                (firstName == data.firstName || (firstName != null && firstName.equals(data.firstName))) &&
                (middleName == data.lastName || (middleName != null && middleName.equals(data.middleName))) &&
                (birthDate == data.birthDate || (birthDate != null && birthDate.equals(data.birthDate)));
    }
	
	@Override
    public int hashCode() {
        final int CODE = (int)(31 * 1 + (lastName == null ? 0 : lastName.hashCode()) + 
        		(firstName == null ? 0 : firstName.hashCode()) + (middleName == null ? 0 : middleName.hashCode()) + 
        		(birthDate == null ? 0 : birthDate.hashCode()));
        return CODE;
        		
	}
	
	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("lastName=" + lastName).add("firstName=" + firstName)
        		.add("middleName=" + middleName).add("birthDate=" + birthDate).toString();
        return objectToString;
	}
}
