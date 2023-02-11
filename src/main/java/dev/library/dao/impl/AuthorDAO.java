package dev.library.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dev.library.dao.ExtraDAO;
import dev.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс для реалиазации интерфейса ExtraDAO типа Author
 * @version 1.0
 */
@Component
public class AuthorDAO implements ExtraDAO<Author> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void create(List<Author> list) {
		final String SQL = "INSERT INTO authors(last_name, first_name, middle_name, birth_date) VALUES(?, ?, ?, ?)";
		
		final BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String lastName = list.get(i).getLastName();
				String firstName = list.get(i).getFirstName();
				String middleName = list.get(i).getMiddleName();
				LocalDate birthDate = list.get(i).getBirthDate();
				
				ps.setString(1, lastName);
				ps.setString(2, firstName);
				ps.setString(3, middleName);
				ps.setDate(4, Date.valueOf(birthDate));
			}
			
			@Override
			public int getBatchSize() {
				return list.size();
			}
		};
		
		jdbcTemplate.batchUpdate(SQL, batchPreparedStatementSetter);
	}

	@Override
	public List<Author> readAll() {
		final String SQL = "SELECT * FROM authors";
		List<Author> authors = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Author.class));
		return authors;
	}

	@Override
	public Optional<Author> read(long id) {
		final String SQL = "SELECT * FROM authors WHERE id = ?";
		Optional<Author> optionalAuthor = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Author.class), id)
				.stream().findAny();
		return optionalAuthor;
	}

	@Override
	public void create(Author entity) {
		throw new UnsupportedOperationException("Method is not supported");
	}

	@Override
	public void update(Author entity, long id) {
		throw new UnsupportedOperationException("Method is not supported");
	}

	@Override
	public void delete(long id) {
		final String SQL = "DELETE FROM authors WHERE id = ?";
		jdbcTemplate.update(SQL, id);
	}

}
