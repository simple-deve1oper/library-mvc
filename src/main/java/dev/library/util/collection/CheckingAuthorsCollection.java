package dev.library.util.collection;

import dev.library.model.Author;
import dev.library.model.Book;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для проверки коллекций объектов типа Author
 * @version 1.0
 */
public class CheckingAuthorsCollection {
    /**
     * Создание карты со списками, элементы которых будут разрешены и запрещены для удаления из базы данных
     * @param authorsFromDb - список объектов типа Author из базы данных
     * @param booksFromDb - список объектов типа Book из базы данных
     * @return карта со списками объектов
     */
    public static Map<String, List<Author>> checkAuthorsFromDb(List<Author> authorsFromDb, List<Book> booksFromDb) {
        List<Author> permit = new ArrayList<>();
        List<Author> restrict = new ArrayList<>();
        if (booksFromDb.isEmpty()) {
            permit.addAll(authorsFromDb);
        } else {
            authorsFromDb.forEach(author -> {
                Optional<Book> optionalBook = booksFromDb.stream()
                        .filter(book -> book.getAuthor().equals(author))
                        .findAny();
                if (optionalBook.isPresent()) {
                    restrict.add(author);
                } else {
                    permit.add(author);
                }
            });
        }

        Map<String, List<Author>> map = new HashMap<>();
        map.put("permit", permit);
        map.put("restrict", restrict);
        return map;
    }

    /**
     * Создание карты со списками, которые будут добавлены и не добавлены в базу данных
     * @param authors - список объектов типа Author после парсинга JSON-файла
     * @param authorsFromDb - список объектов типа Author из базы данных
     * @return карта со списками объектов из JSON-файла
     */
    public static Map<String, List<Author>> checkAuthorsFromJsonFile(List<Author> authors, List<Author> authorsFromDb) {
        List<Author> ok = new ArrayList<>();
        List<Author> notOk = new ArrayList<>();

        if (authorsFromDb.isEmpty()) {
            ok.addAll(authors);
        } else {
            ok.addAll(authors.stream().filter(author -> !checkItemInList(author, authorsFromDb))
                    .filter(author -> checkObjectContents(author)).collect(Collectors.toList()));
            notOk.addAll(authors.stream().filter(author -> checkItemInList(author, authorsFromDb)).collect(Collectors.toList()));
            notOk.addAll(ok.stream().filter(author -> !checkObjectContents(author)).collect(Collectors.toList()));
        }

        Map<String, List<Author>> map = new HashMap<>();
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
    private static boolean checkItemInList(Author item, List<Author> list) {
        for (Author author : list) {
            if (author.getFirstName().equals(item.getFirstName()) && author.getLastName().equals(item.getLastName()) &&
                    author.getMiddleName().equals(item.getMiddleName()) && author.getBirthDate().equals(item.getBirthDate())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверка наличия данных в объекте
     * @param author - объект типа Author
     * @return результат наличия данных в объекте
     */
    private static boolean checkObjectContents(Author author) {
        if ((author.getFirstName() != null) && (author.getLastName() != null) &&
                (author.getMiddleName() != null) && (author.getBirthDate() != null)) {
            return true;
        }

        return false;
    }
}
