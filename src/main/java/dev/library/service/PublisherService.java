package dev.library.service;

import dev.library.dao.impl.PublisherDAO;
import dev.library.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    private PublisherDAO publisherDAO;

    /**
     * Добавление издательств из файла JSON
     * @param publishers - список объектов типа Author
     */
    public void addAuthorsFromJson(List<Publisher> publishers) {
        publisherDAO.create(publishers);
    }
    /**
     * Получение всех издательств
     * @return список объектов типа Publisher
     */
    public List<Publisher> readAllPublishers() {
        List<Publisher> publishers = publisherDAO.readAll();
        return publishers;
    }
    /**
     * Получение издательства по идентификатору
     * @param id - идентификатор
     * @return класс-оболочка типа Publisher
     */
    public Optional<Publisher> getPublisher(long id) {
        Optional<Publisher> optionalPublisher = publisherDAO.read(id);
        return optionalPublisher;
    }
    /**
     * Удаление издательства по идентификатору
     * @param id - идентификатор
     * @return результат удаления издательства из базы данных
     */
    public boolean deletePublisher(long id) {
        if (publisherDAO.read(id).isEmpty()) {
            return false;
        }

        publisherDAO.delete(id);
        return true;
    }
}
