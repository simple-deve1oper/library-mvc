package dev.library.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dev.library.dao.DAO;
import dev.library.dao.mapper.PersonMapper;
import dev.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс для реалиазации интерфейса DAO типа Person
 * @version 1.0
 */
@Component
public class PersonDAO implements DAO<Person> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PersonMapper personMapper;
	
	@Override
	public void create(Person entity) {
		String lastName = entity.getBasicData().getLastName();
		String firstName = entity.getBasicData().getFirstName(); 
		String middleName = entity.getBasicData().getMiddleName(); 
		LocalDate birthDate = entity.getBasicData().getBirthDate();
		String email = entity.getAdditionalData().getEmail();
		String phoneNumber = entity.getAdditionalData().getPhoneNumber();
		
		if ((middleName == null) && (email == null)) {
			final String SQL = "INSERT INTO people(last_name, first_name, birth_date, phone_number) VALUES(?, ?, ?, ?)";
			jdbcTemplate.update(SQL, lastName, firstName, birthDate, phoneNumber);
		} else if (middleName == null) {
			final String SQL = "INSERT INTO people(last_name, first_name, birth_date, email, phone_number) VALUES(?, ?, ?, ?, ?)";
			jdbcTemplate.update(SQL, lastName, firstName, birthDate, email, phoneNumber);
		} else if (email == null) {
			final String SQL = "INSERT INTO people(last_name, first_name, middle_name, birth_date, phone_number) VALUES(?, ?, ?, ?, ?)";
			jdbcTemplate.update(SQL, lastName, firstName, middleName, birthDate, phoneNumber);
		} else {
			final String SQL = "INSERT INTO people(last_name, first_name, middle_name, birth_date, email, phone_number) VALUES(?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(SQL, lastName, firstName, middleName, birthDate, email, phoneNumber);
		}
	}

	@Override
	public List<Person> readAll() {
		final String SQL = "SELECT * FROM people";
		List<Person> people = jdbcTemplate.query(SQL, personMapper);
		return people;
	}

	@Override
	public Optional<Person> read(long id) {
		final String SQL = "SELECT * FROM people WHERE id = ?";
		Optional<Person> optionalPerson = jdbcTemplate.query(SQL, personMapper, id)
				.stream().findAny();
		return optionalPerson;
	}

	@Override
	public void update(Person entity, long id) {
		String lastName = entity.getBasicData().getLastName();
		String firstName = entity.getBasicData().getFirstName(); 
		String middleName = entity.getBasicData().getMiddleName(); 
		LocalDate birthDate = entity.getBasicData().getBirthDate();
		String email = entity.getAdditionalData().getEmail();
		String phoneNumber = entity.getAdditionalData().getPhoneNumber();
		
		if ((middleName == null) && (email == null)) {
			final String SQL = "UPDATE people SET last_name = ?, first_name = ?, birth_date = ?, phone_number = ? WHERE id = ?";
			jdbcTemplate.update(SQL, lastName, firstName, birthDate, phoneNumber, id);
		} else if (middleName == null) {
			final String SQL = "UPDATE people SET last_name = ?, first_name = ?, birth_date = ?, email = ?, phone_number = ? WHERE id = ?";
			jdbcTemplate.update(SQL, lastName, firstName, birthDate, email, phoneNumber, id);
		} else if (email == null) {
			final String SQL = "UPDATE people SET last_name = ?, first_name = ?, middle_name = ?, birth_date = ?, phone_number = ? WHERE id = ?";
			jdbcTemplate.update(SQL, lastName, firstName, middleName, birthDate, phoneNumber, id);
		} else {
			final String SQL = "UPDATE people SET last_name = ?, first_name = ?, middle_name = ?, birth_date = ?, email = ?, phone_number = ? WHERE id = ?";
			jdbcTemplate.update(SQL, lastName, firstName, middleName, birthDate, email, phoneNumber, id);
		}
	}

	@Override
	public void delete(long id) {
		throw new UnsupportedOperationException("Method is not supported");
	}

	/**
	 * Чтение объекта по номеру телефона
	 * @param phoneNumber - номер телефона
	 * @return класс-оболочка Optional типа Person
	 */
	public Optional<Person> read(String phoneNumber) {
		final String SQL = "SELECT * FROM people WHERE phone_number = ?";
		Optional<Person> optionalPerson = jdbcTemplate.query(SQL, personMapper, phoneNumber)
				.stream().findAny();
		return optionalPerson;
	}
}
