package dev.library.util.data;

import dev.library.model.Publisher;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Класс с неизменяемыми данными, содержащий список объектов типа Publisher
 * @param publishers - список объектов типа Publisher
 * @version 1.0
 */
public record ListPublishers(List<Publisher> publishers) {
    /**
     * Конструктор для создания нового объекта типа ListPublishers
     * @param publishers - список объектов типа Publisher
     */
    @JsonCreator
    public ListPublishers(@JsonProperty("publishers") List<Publisher> publishers){
        this.publishers = publishers;
    }
}
