package dev.library.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

/**
 * Класс для описания информации о книге для HTML-формы
 * @version 1.0
 */
public class BookForm {
    /* наименование */
    @NotNull(message = "Поле 'Наименование' не может быть пустым")
    @Size(min = 1, max = 255, message = "Поле 'Наименование' может содержать от 1 до 255 символов")
    private String name;
    /* идентификатор автора */
    @NotNull(message = "Поле 'Автор' не может быть пустым")
    private long authorId;
    /* идентификатор издательства */
    @NotNull(message = "Поле 'Издательство' не может быть пустым")
    private long publisherId;
    /* год издания */
    @NotNull(message = "Поле 'Год издания' не может быть пустым")
    @Pattern(regexp = "\\d+", message = "Поле 'Год издания' может содержать только числа")
    private String publicationYear;

    /**
     * Конструктор для создания нового объекта типа BookForm
     * @param name - наименование
     * @param authorId - идентификатор автора
     * @param publisherId - идентификатор издательства
     * @param publicationYear - год издания
     */
    public BookForm(String name, long authorId, long publisherId, String publicationYear) {
        this.name = name;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.publicationYear = publicationYear;
    }
    /**
     * Конструктор по умолчанию для создания нового объекта типа BookForm
     */
    public BookForm() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
    public long getPublisherId() {
        return publisherId;
    }
    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }
    public String getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("name=" + name).add("authorId=" + authorId)
                .add("publisherId=" + publisherId).add("publicationYear=" + publicationYear).toString();
        return objectToString;
    }
}
