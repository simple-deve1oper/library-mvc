package dev.library.dao.impl;

import dev.library.dao.DAO;
import dev.library.dao.mapper.BookMapper;
import dev.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Класс для реалиазации интерфейса DAO типа Book
 * @version 1.0
 */
@Component
public class BookDAO implements DAO<Book> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public void create(Book entity) {
        String name = entity.getName();
        long authorId = entity.getAuthor().getId();
        long publisherId = entity.getPublisher().getId();
        String publicationYear = entity.getPublicationYear();
        boolean available = entity.isAvailable();

        final String SQL = "INSERT INTO books(name, author_id, publisher_id, publication_year, available) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(SQL, name, authorId, publisherId, publicationYear, available);
    }

    @Override
    public void update(Book entity, long id) {
        String name = entity.getName();
        long authorId = entity.getAuthor().getId();
        long publisherId = entity.getPublisher().getId();
        String publicationYear = entity.getPublicationYear();
        boolean available = entity.isAvailable();

        final String SQL = "UPDATE books SET name = ?, author_id = ?, publisher_id = ?, publication_year = ?, available = ? WHERE id = ?";
        jdbcTemplate.update(SQL, name, authorId, publisherId, publicationYear, available, id);
    }

    @Override
    public void delete(long id) {
        final String SQL = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public List<Book> readAll() {
        final String SQL = "SELECT * FROM books";
        List<Book> books = jdbcTemplate.query(SQL, bookMapper);
        return books;
    }

    @Override
    public Optional<Book> read(long id) {
        final String SQL = "SELECT * FROM books WHERE id = ?";
        Optional<Book> optionalBook = jdbcTemplate.query(SQL, bookMapper, id)
                .stream().findAny();
        return optionalBook;
    }
}
