package dev.library.util.data.parse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import dev.library.util.data.ListPublishers;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.library.util.data.ListAuthors;

/**
 * Класс для парсинга JSON-файла
 * @version 1.0
 */
public class JsonParse {
	/**
	 * Десериализация JSON-файла в объект типа ListAuthors
	 * @param pathToFile - путь к файлу
	 * @return класс-оболочка Optional типа ListAuthors
	 */
	public static Optional<ListAuthors> parseAuthorsFromJSON(String pathToFile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
			ListAuthors listAuthors = mapper.readValue(reader, ListAuthors.class);
			return Optional.of(listAuthors);
		} catch (FileNotFoundException ex) {
            System.err.println("Файл с данными об авторах не найден: " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("Ошибка парсинга файла с данными об авторах: " + ex.getMessage());
        }
		
		return Optional.empty();
	}

	/**
	 * Десериализация JSON-файла в объект типа ListAuthors
	 * @param pathToFile - путь к файлу
	 * @return класс-оболочка Optional типа ListPublishers
	 */
	public static Optional<ListPublishers> parsePublishersFromJSON(String pathToFile) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
			ListPublishers listPublishers = mapper.readValue(reader, ListPublishers.class);
			return Optional.of(listPublishers);
		} catch (FileNotFoundException ex) {
			System.err.println("Файл с данными об издателях не найден: " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("Ошибка парсинга файла с данными об издателях: " + ex.getMessage());
		}

		return Optional.empty();
	}
}
