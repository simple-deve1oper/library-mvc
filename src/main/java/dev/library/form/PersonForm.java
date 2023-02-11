package dev.library.form;

import java.util.StringJoiner;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Класс для описания информации о человеке для HTML-формы
 * @version 1.0
 */
public class PersonForm {
	/* фамилия */
	@NotNull(message = "Поле 'Фамилия' не может быть пустым")
	@Size(min = 1, max = 60, message = "Поле 'Фамилия' может содержать от 1 до 60 символов")
	@Pattern(regexp = "[А-ЯЁ]{1}[а-яё]+", message = "Поле 'Фамилия' должно содержать только русские буквы, первая буква должна быть заглавной")
	private String lastName;
	/* имя */
	@NotNull(message = "Поле 'Имя' не может быть пустым")
	@Size(min = 1, max = 60, message = "Поле 'Имя' может содержать от 1 до 60 символов")
	@Pattern(regexp = "[А-ЯЁ]{1}[а-яё]+", message = "Поле 'Имя' должно содержать только русские буквы, первая буква должна быть заглавной")
	private String firstName;
	/* отчество */
	@Pattern(regexp = "([А-ЯЁ]{1}[а-яё]+)*", message = "Поле 'Отчество' должно содержать только русские буквы, первая буква должна быть заглавной")
	private String middleName;
	/* дата рождения */
	@NotNull
	private String birthDate;
	/* email */
	@Pattern(regexp = "([a-z0-9]+\\@[a-z]+\\.[a-z]+)*", message = "Поле 'Email' должно соответствовать следующему паттерну: test@localhost.org")
	private String email;
	/* номер телефона */
	@NotNull
	@Pattern(regexp = "\\+7\\([0-9]{1,3}\\)[0-9]{3}-[0-9]{2}-[0-9]{2}", message = "Поле 'Номер телефона' должно соответствовать следующему паттерну: +7(999)999-99-99")
	private String phoneNumber;

	/**
	 * Конструктор для создания нового объекта типа PersonForm
	 * @param lastName - фамилия
	 * @param firstName - имя
	 * @param middleName - отчество
	 * @param birthDate - дата рождения
	 * @param email - email
	 * @param phoneNumber - номер телефона
	 */
	public PersonForm(String lastName, String firstName, String middleName, String birthDate, String email,
			String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.birthDate = birthDate;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	/**
	 * Конструктор по умолчанию для создания нового объекта типа PersonForm
	 */
	public PersonForm() {}

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
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("lastName=" + lastName).add("firstName=" + firstName)
        		.add("middleName=" + middleName).add("birthDate=" + birthDate).add("email=" + email)
        		.add("phoneNumber=" + phoneNumber).toString();
        return objectToString;
	}
}
