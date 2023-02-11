package dev.library.dao;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для описания базовых методов DAO
 * @param <T> - тип класса
 * @version 1.0
 */
public interface DAO<T> {
	/**
	 * Чтение всех объектов
	 * @return список объектов
	 */
	List<T> readAll();
	/**
	 * Чтение объекта по идентификатору
	 * @param id - идентификатор
	 * @return класс оболочка Optional
	 */
	Optional<T> read(long id);
	/**
	 * Создание объекта
	 * @param entity - сущность
	 */
	void create(T entity);
	/**
	 * Обновление объекта
	 * @param entity - сущность
	 * @param id - идентификатор
	 */
	void update(T entity, long id);
	/**
	 * Удаление объекта по идентификатору
	 * @param id - идентификатор
	 */
	void delete(long id);
}
