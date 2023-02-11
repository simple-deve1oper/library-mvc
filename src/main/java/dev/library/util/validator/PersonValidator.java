package dev.library.util.validator;

import dev.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import dev.library.dao.impl.PersonDAO;

/**
 * Класс, реализующий интерфейс Validator для сравнения номера телефона объектов типа Person
 * @version 1.0
 */
@Component
public class PersonValidator implements Validator {
	@Autowired
	public PersonDAO personDAO;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		
		if (personDAO.read(person.getAdditionalData().getPhoneNumber()).isPresent()) {
			errors.rejectValue("phoneNumber", "", "Этот номер телефона уже используется");
		}
	}

}
