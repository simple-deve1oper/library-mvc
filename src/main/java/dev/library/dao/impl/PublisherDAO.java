package dev.library.dao.impl;

import dev.library.dao.ExtraDAO;
import dev.library.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Класс для реалиазации интерфейса ExtraDAO типа Publisher
 * @version 1.0
 */
@Component
public class PublisherDAO implements ExtraDAO<Publisher> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Publisher> readAll() {
        final String SQL = "SELECT * FROM publishers";
        List<Publisher> authors = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Publisher.class));
        return authors;
    }

    @Override
    public Optional<Publisher> read(long id) {
        final String SQL = "SELECT * FROM publishers WHERE id = ?";
        Optional<Publisher> optionalPublisher = jdbcTemplate.query(SQL, new BeanPropertyRowMapper<>(Publisher.class), id)
                .stream().findAny();
        return optionalPublisher;
    }

    @Override
    public void create(Publisher entity) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public void update(Publisher entity, long id) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public void create(List<Publisher> list) {
        final String SQL = "INSERT INTO publishers(name) VALUES(?)";

        final BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String name = list.get(i).getName();

                ps.setString(1, name);
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        };

        jdbcTemplate.batchUpdate(SQL, batchPreparedStatementSetter);
    }

    @Override
    public void delete(long id) {
        final String SQL = "DELETE FROM publishers WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
