package dev.library.dao.mapper;

import dev.library.dao.impl.BookDAO;
import dev.library.dao.impl.PersonDAO;
import dev.library.model.Book;
import dev.library.model.Person;
import dev.library.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Класс, реализующий интерфейс RowMapper для объекта типа Report
 * @version 1.0
 */
@Component
public class ReportMapper implements RowMapper<Report> {
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;

    @Override
    public Report mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        long personId = rs.getLong("person_id");
        Optional<Person> optionalPerson = personDAO.read(personId);
        if (optionalPerson.isEmpty()) {
            throw new SQLException("Объекта типа Person с идентификатором " + personId + " не найдено");
        }
        Person person = optionalPerson.get();
        long bookId = rs.getLong("book_id");
        Optional<Book> optionalBook = bookDAO.read(bookId);
        if (optionalBook.isEmpty()) {
            throw new SQLException("Объекта типа Book с идентификатором " + bookId + " не найдено");
        }
        Book book = optionalBook.get();
        LocalDateTime issueDate = rs.getTimestamp("issue_date").toLocalDateTime();
        LocalDateTime returnDate = null;
        if (rs.getTimestamp("return_date") != null) {
            returnDate = rs.getTimestamp("return_date").toLocalDateTime();
        }

        Report report = null;
        if (returnDate != null) {
            report = new Report(id, person, book, issueDate, returnDate);
        } else {
            report = new Report(id, person, book, issueDate);
        }
        return report;
    }
}
