package dev.library.util.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.library.model.Author;

/**
 * Класс с неизменяемыми данными, содержащий список объектов типа Author
 * @param authors - список объектов типа Author
 * @version 1.0
 */
public record ListAuthors(List<Author> authors) {
    /**
     * Конструктор для создания нового объекта типа ListAuthors
     * @param authors - список объектов типа Author
     */
    @JsonCreator
    public ListAuthors(@JsonProperty("authors") List<Author> authors) {
        this.authors = authors;
    }
}
