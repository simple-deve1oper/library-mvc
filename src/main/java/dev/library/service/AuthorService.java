package dev.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.library.dao.impl.AuthorDAO;
import dev.library.model.Author;

@Service
public class AuthorService {
	@Autowired
	private AuthorDAO authorDAO;

	/**
	 * Добавление авторов из файла JSON
	 * @param authors - список объектов типа Author
	 */
	public void addAuthorsFromJson(List<Author> authors) {
		authorDAO.create(authors);
	}
	/**
	 * Получение всех авторов
	 * @return список объектов типа Author
	 */
	public List<Author> readAllAuthors() {
		List<Author> authors = authorDAO.readAll();
		return authors;
	}
	/**
	 * Получение автора по идентификатору
	 * @param id - идентификатор
	 * @return класс-оболочка Optional типа Author
	 */
	public Optional<Author> getAuthor(long id) {
		Optional<Author> optionalAuthor = authorDAO.read(id);
		return optionalAuthor;
	}
	/**
	 * Удаление автора по идентификатору
	 * @param id - идентификатор
	 * @return результат удаления автора из базы данных
	 */
	public boolean deleteAuthor(long id) {
		if (authorDAO.read(id).isEmpty()) {
			return false;
		}
		
		authorDAO.delete(id);
		return true;
	}
}
