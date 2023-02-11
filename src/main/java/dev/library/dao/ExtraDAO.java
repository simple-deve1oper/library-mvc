package dev.library.dao;

import java.util.List;

/**
 * Интерфейс для описания дополнительных методов DAO
 * @param <T> - тип класса
 * @version 1.0
 */
public interface ExtraDAO<T> extends DAO<T> {
    /**
     * Создание нескольких объектов
     * @param list - список объектов
     */
    void create(List<T> list);
}
