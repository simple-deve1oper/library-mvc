package dev.library.dao.mapper;

import dev.library.dao.impl.AuthorDAO;
import dev.library.dao.impl.PublisherDAO;
import dev.library.model.Author;
import dev.library.model.Book;
import dev.library.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс, реализующий интерфейс RowMapper для объекта типа Book
 * @version 1.0
 */
@Component
public class BookMapper implements RowMapper<Book> {
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private PublisherDAO publisherDAO;

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        long authorId = rs.getLong("author_id");
        Optional<Author> optionalAuthor = authorDAO.read(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new SQLException("Объекта типа Author с идентификатором " + authorId + " не найдено");
        }
        Author author = optionalAuthor.get();
        long publisher_id = rs.getLong("publisher_id");
        Optional<Publisher> optionalPublisher = publisherDAO.read(publisher_id);
        if (optionalPublisher.isEmpty()) {
            throw new SQLException("Объекта типа Publisher с идентификатором " + publisher_id + " не найдено");
        }
        Publisher publisher = optionalPublisher.get();
        String publicationYear = rs.getString("publication_year");
        boolean available = rs.getBoolean("available");

        Book book = new Book(id, name, author, publisher, publicationYear, available);
        return book;
    }
}
