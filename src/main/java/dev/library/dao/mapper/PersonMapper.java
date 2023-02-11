package dev.library.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import dev.library.model.Person;
import dev.library.model.component.AdditionalData;
import dev.library.model.component.BasicData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Класс, реализующий интерфейс RowMapper для объекта типа Person
 * @version 1.0
 */
@Component
public class PersonMapper implements RowMapper<Person> {
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id = rs.getLong("id");
		String lastName = rs.getString("last_name");
		String firstName = rs.getString("first_name");
		String middleName = rs.getString("middle_name");
		LocalDate birthDate = rs.getDate("birth_date").toLocalDate();
		String email = rs.getString("email");
		String phoneNumber = rs.getString("phone_number");
		
		BasicData basicData = new BasicData();
		basicData.setLastName(lastName);
		basicData.setFirstName(firstName);
		if (middleName != null) {
			basicData.setMiddleName(middleName);
		}
		basicData.setBirthDate(birthDate);
		
		AdditionalData additionalData = new AdditionalData();
		if (email != null) {
			additionalData.setEmail(email);
		}
		additionalData.setPhoneNumber(phoneNumber);
		
		Person person = new Person();
		person.setId(id);
		person.setBasicData(basicData);
		person.setAdditionalData(additionalData);
		return person;
	}

}
