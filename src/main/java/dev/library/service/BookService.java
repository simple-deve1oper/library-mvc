package dev.library.service;

import dev.library.dao.impl.BookDAO;
import dev.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookDAO bookDAO;

    /**
     * Добавление книги
     * @param book - книга
     */
    public void addBook(Book book) {
        bookDAO.create(book);
    }
    /**
     * Получение книги по идентификатору
     * @param id - идентификатор
     * @return класс-оболочка Optional типа Book
     */
    public Optional<Book> getBook(long id) {
        Optional<Book> optional = bookDAO.read(id);
        return optional;
    }
    /**
     * Получение всех книг
     * @return список объектов типа Book
     */
    public List<Book> getAllBooks() {
        List<Book> books = bookDAO.readAll();
        return books;
    }
    /**
     * Обновление информации о книге
     * @return результат обновления информации о книге
     */
    public boolean updateInfo(Book book, long id) {
        if (bookDAO.read(id).isEmpty()) {
            return false;
        }
        bookDAO.update(book, id);
        return true;
    }
    /**
     * Удаление книги по идентификатору
     * @param id - идентификатор
     * @return результат удаления книги из базы данных
     */
    public boolean deleteBook(long id) {
        if (bookDAO.read(id).isEmpty()) {
            return false;
        }

        bookDAO.delete(id);
        return true;
    }
}
