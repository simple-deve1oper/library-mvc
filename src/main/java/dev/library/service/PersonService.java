package dev.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.library.dao.impl.PersonDAO;
import dev.library.model.Person;

@Service
public class PersonService {
	@Autowired
	private PersonDAO personDAO;
	
	/**
	 * Регистрация читателя
	 * @param person - человек
	 */
	public void personRegistration(Person person) {
		personDAO.create(person);
	}
	/**
	 * Получение читателя по идентификатору
	 * @param id - идентификатор
	 * @return класс-оболочка Optional типа Person
	 */
	public Optional<Person> getPerson(long id) {
		Optional<Person> optional = personDAO.read(id);
		return optional;
	}
	/**
	 * Получение всех читателей
	 * @return список объектов типа Person
	 */
	public List<Person> getAllPeople() {
		List<Person> people = personDAO.readAll();
		return people;
	}
	/**
	 * Обновление информации о читателе
	 * @return результат обновления информации о человеке
	 */
	public boolean updateInfo(Person person, long id) {
		if (personDAO.read(id).isEmpty()) {
			return false;
		}
		personDAO.update(person, id);
		return true;
	}
}
