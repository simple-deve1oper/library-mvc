package dev.library.dao.impl;

import dev.library.dao.DAO;
import dev.library.dao.mapper.ReportMapper;
import dev.library.model.Book;
import dev.library.model.Person;
import dev.library.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Класс для реалиазации интерфейса DAO типа Report
 * @version 1.0
 */
@Component
public class ReportDAO implements DAO<Report> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<Report> readAll() {
        final String SQL = "SELECT * FROM reports";
        List<Report> reports = jdbcTemplate.query(SQL, reportMapper);
        return reports;
    }

    @Override
    public Optional<Report> read(long id) {
        final String SQL = "SELECT * FROM reports WHERE id = ?";
        Optional<Report> optionalReport = jdbcTemplate.query(SQL, reportMapper, id)
                .stream().findAny();
        return optionalReport;
    }

    @Override
    public void create(Report entity) {
        long personId = entity.getPerson().getId();
        long bookId = entity.getBook().getId();
        LocalDateTime issueDate = entity.getIssueDate();

        final String SQL = "INSERT INTO reports(person_id, book_id, issue_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(SQL, personId, bookId, issueDate);
    }

    @Override
    public void update(Report entity, long id) {
        long personId = entity.getPerson().getId();
        long bookId = entity.getBook().getId();
        LocalDateTime issueDate = entity.getIssueDate();
        LocalDateTime returnDate = entity.getReturnDate();

        final String SQL = "UPDATE reports SET person_id = ?, book_id = ?, issue_date = ?, return_date = ? WHERE id = ?";
        jdbcTemplate.update(SQL, personId, bookId, issueDate, returnDate, id);
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    /**
     * Поиск отчёта, в котором книга не возвращена в библиотеку
     * @param book - книга
     * @return - класс-оболочка Optional типа Report
     */
    public Optional<Report> findIssuedBook(Book book) {
        final String SQL = "SELECT * FROM reports WHERE book_id = ? AND return_date IS NULL";
        long bookId = book.getId();
        Optional<Report> optionalReport = jdbcTemplate.query(SQL, reportMapper, bookId).stream().findAny();
        return optionalReport;
    }

    /**
     * Поиск отчётов по человеку, который не вернул книги в библиотеку
     * @param person - человек
     * @return список объектов типа Report
     */
    public List<Report> findIssuedBooks(Person person) {
        final String SQL = "SELECT * FROM reports WHERE person_id = ? AND return_date IS NULL";
        long personId = person.getId();
        List<Report> reports = jdbcTemplate.query(SQL, reportMapper, personId);
        return reports;
    }
}
