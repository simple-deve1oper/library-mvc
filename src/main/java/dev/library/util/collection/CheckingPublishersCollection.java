package dev.library.util.collection;

import dev.library.model.Book;
import dev.library.model.Publisher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для проверки коллекций объектов типа Publisher
 * @version 1.0
 */
public class CheckingPublishersCollection {
    /**
     * Создание карты со списками, элементы которых будут разрешены и запрещены для удаления из базы данных
     * @param publishersFromDb - список объектов типа Publisher из базы данных
     * @param booksFromDb - список объектов типа Book из базы данных
     * @return карта со списками объектов
     */
    public static Map<String, List<Publisher>> checkPublishersFromDb(List<Publisher> publishersFromDb, List<Book> booksFromDb) {
        List<Publisher> permit = new ArrayList<>();
        List<Publisher> restrict = new ArrayList<>();
        if (booksFromDb.isEmpty()) {
            permit.addAll(publishersFromDb);
        } else {
            publishersFromDb.forEach(publisher -> {
                Optional<Book> optionalBook = booksFromDb.stream()
                        .filter(book -> book.getPublisher().equals(publisher))
                        .findAny();
                if (optionalBook.isPresent()) {
                    restrict.add(publisher);
                } else {
                    permit.add(publisher);
                }
            });
        }

        Map<String, List<Publisher>> map = new HashMap<>();
        map.put("permit", permit);
        map.put("restrict", restrict);
        return map;
    }

    /**
     * Создание карты со списками, состоящих из объектов из JSON-файла
     * @param publishers - список объектов типа Publisher после парсинга JSON-файла
     * @param publishersFromDb - список объектов типа Publisher из базы данных
     * @return карта со списками объектов из JSON-файла
     */
    public static Map<String, List<Publisher>> checkPublishersFromJsonFile(List<Publisher> publishers, List<Publisher> publishersFromDb) {
        List<Publisher> ok = new ArrayList<>();
        List<Publisher> notOk = new ArrayList<>();

        if (publishersFromDb.isEmpty()) {
            ok.addAll(publishers);
        } else {
            ok.addAll(publishers.stream().filter(publisher -> !checkItemInList(publisher, publishersFromDb))
                    .filter(publisher -> checkObjectContents(publisher)).collect(Collectors.toList()));
            notOk.addAll(publishers.stream().filter(publisher -> checkItemInList(publisher, publishersFromDb)).collect(Collectors.toList()));
            notOk.addAll(ok.stream().filter(publisher -> !checkObjectContents(publisher)).collect(Collectors.toList()));
        }

        Map<String, List<Publisher>> map = new HashMap<>();
        map.put("ok", ok);
        map.put("notOk", notOk);
        return map;
    }

    /**
     * Проверка на наличие объекта в списке
     * @param item - объект
     * @param list - список
     * @return результат наличия объекта в списке
     */
    private static boolean checkItemInList(Publisher item, List<Publisher> list) {
        for (Publisher publisher : list) {
            if (publisher.getName().equals(item.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверка наличия данных в объекте
     * @param publisher - объект типа Publisher
     * @return результат наличия данных в объекте
     */
    private static boolean checkObjectContents(Publisher publisher) {
        if ((publisher.getName() != null)) {
            return true;
        }

        return false;
    }


}
