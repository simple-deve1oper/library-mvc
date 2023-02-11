package dev.library.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для обработки файлов стандартными средствами Java
 * @version 1.0
 */
public class FileProcessing {
    /**
     * Создание директории с переданным именем, если не существует такой
     * @param directory - название директории
     */
    public static void createDirectory(String directory) {
        Path path = Paths.get(directory);
        if (!isExists(directory)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.err.println("Ошибка создания директории '" + directory.replaceAll("/", "")
                        + "': " + e.getMessage());
            }
        }
    }

    /**
     * Проверка на существование директории/файла по переданному пути
     * @param pathTo - путь к директории/файлу
     * @return результат существование директории/файла по переданному пути
     */
    public static boolean isExists(String pathTo) {
        Path path = Paths.get(pathTo);
        return Files.exists(path);
    }

    /**
     * Сохранение файла по переданному пути
     * @param file - файл, полученный из формы
     * @param pathToFile - путь к файлу
     */
    public static void saveFile(MultipartFile file, String pathToFile) {
        Path path = Paths.get(pathToFile);
        try {
            file.transferTo(path);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения файла по пути '" + pathToFile + "': " + e.getMessage());
        }
    }

    /**
     * Удаление файла по переданному пути
     * @param pathToFile - путь к файлу
     * @return результат удаления файла по переданному пути
     */
    public static boolean deleteFile(String pathToFile) {
        if (pathToFile != null && isExists(pathToFile)) {
            Path path = Paths.get(pathToFile);
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.err.println("Ошибка удаления файла по пути '" + pathToFile + "': " + e.getMessage());
            }
            return true;
        }
        return false;
    }
}
