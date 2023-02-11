package dev.library.util.file;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для обработки файлов с помощью регулярного выражения
 * @version 1.0
 */
public class RegexProcessing {
    /* счётчик для названия файла */
    private static long count = 2;

    /**
     * Разделение пути файла на элементы массива с помощью регулярного выражения
     * @param regex - регулярное выражение
     * @param pathToFile - путь к файлу
     * @return разделенный путь к файлу на элементы массива
     */
    public static String[] getArrayAfterSplit(String regex, String pathToFile) {
        Pattern pattern = Pattern.compile(regex);
        String[] data = pattern.split(pathToFile);
        return data;
    }

    /**
     * Поиск имени файла в массиве
     * @param data - массив
     * @return имя файла без директории
     */
    public static String findFileNameFromArray(String[] data) {
        for (String str : data) {
            Pattern patternStr = Pattern.compile("[A-Za-zА-Яа-я0-9]+\\.[A-Za-z]+");
            Matcher matcher = patternStr.matcher(str);
            if (matcher.matches()) {
                return str;
            }
        }

        return "";
    }

    /**
     * Переименование файла с помощью счётчика и построение пути к нему с помощью присоединения имени директории
     * @param pathToFile - путь к файлу
     * @param directoryName - имя директории, в которой должен находиться файл
     * @return путь к переименованному файлу
     */
    public static String renameFileCount(String pathToFile, String directoryName) {
        String[] data = getArrayAfterSplit("/" , pathToFile);
        String fileNameWithoutDirectory = findFileNameFromArray(data);
        data = getArrayAfterSplit("\\.", fileNameWithoutDirectory);
        String extension = data[1];
        String fileNameWithoutExtension = data[0];
        data = getArrayAfterSplit("[0-9]+", fileNameWithoutExtension);
        fileNameWithoutExtension = data[0];
        fileNameWithoutExtension += count++;
        fileNameWithoutDirectory = fileNameWithoutExtension + "." + extension;
        pathToFile = directoryName + "/" + fileNameWithoutDirectory;
        return pathToFile;
    }
}
