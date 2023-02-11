package dev.library.util;

import dev.library.model.Report;
import dev.library.util.file.FileProcessing;
import dev.library.util.file.RegexProcessing;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Класс для работы со сводной таблицей
 * @version 1.0
 */
public class PivotTable {
    private final String pathToReport;

    public PivotTable(String pathToReport) {
        this.pathToReport = pathToReport;
    }

    /**
     * Создание отчёта в формате .xlsx
     * @param reports - отчёт
     * @return путь к созданному отчёту
     * @throws IOException - исключение ввода-вывода
     */
    public String createReport(List<Report> reports) throws IOException {
        final Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчёт о книгах");
        String[] columnHeadings = {"Номер", "Читатель", "Номер телефона читателя", "Название книги", "Автор",
                "Год издания", "Дата выдачи", "Дата возврата"};

        Font headerFont = createAndCustomizeFont(workbook, true, (short) 12, IndexedColors.BLACK.index);

        CellStyle headerStyle = workbook.createCellStyle();
        customizeCellStyle(headerStyle, headerFont, FillPatternType.SOLID_FOREGROUND, IndexedColors.AQUA.index);

        Row headerRow = sheet.createRow(0);
        fillHeader(columnHeadings, headerRow, headerStyle);

        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        int rowNum = 1;
        for (Report report : reports) {
            Row row = sheet.createRow(rowNum++);
            fillData(report, row, dateStyle);
        }

        for (int i = 0; i < columnHeadings.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String pathToFile = writeFileToDisk(workbook, pathToReport);
        workbook.close();
        return pathToFile;
    }

    /**
     * Создание и настройка объекта типа Font
     * @return объект типа Font
     */
    private Font createAndCustomizeFont(Workbook workbook, boolean bold, short size, short color) {
        Font font = workbook.createFont();
        font.setBold(bold);
        font.setFontHeightInPoints(size);
        font.setColor(color);
        return font;
    }

    /**
     * Настройка объекта типа CellStyle
     */
    private void customizeCellStyle(CellStyle cellStyle, Font font, FillPatternType type, short foregroundColor) {
        cellStyle.setFont(font);
        cellStyle.setFillPattern(type);
        cellStyle.setFillForegroundColor(foregroundColor);
    }

    /**
     * Заполнение заголовка
     */
    private void fillHeader(String[] columnHeadings, Row headerRow, CellStyle headerStyle) {
        for (int i = 0; i < columnHeadings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeadings[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * Заполнение данных
     */
    private void fillData(Report report, Row row, CellStyle dateStyle) {
        row.createCell(0).setCellValue(report.getId());
        String lastNamePerson = report.getPerson().getBasicData().getLastName();
        String firstNamePerson = report.getPerson().getBasicData().getFirstName();
        String middleNamePerson = report.getPerson().getBasicData().getMiddleName();
        String fullNamePerson = lastNamePerson + " " + firstNamePerson +
                (middleNamePerson == null ? "" : " " + middleNamePerson);
        row.createCell(1).setCellValue(fullNamePerson);
        row.createCell(2).setCellValue(report.getPerson().getAdditionalData().getPhoneNumber());
        row.createCell(3).setCellValue(report.getBook().getName());

        String lastNameAuthor = report.getBook().getAuthor().getLastName();
        String firstNameAuthor = report.getBook().getAuthor().getFirstName();
        String middleNameAuthor = report.getBook().getAuthor().getMiddleName();
        String fullNameAuthor = lastNameAuthor + " " + firstNameAuthor +
                (middleNameAuthor == null ? "" : " " + middleNameAuthor);
        row.createCell(4).setCellValue(fullNameAuthor);
        row.createCell(5).setCellValue(report.getBook().getPublicationYear());

        Cell dateCell = row.createCell(6);
        dateCell.setCellValue(report.getIssueDate());
        dateCell.setCellStyle(dateStyle);
        if (report.getReturnDate() != null) {
            dateCell = row.createCell(7);
            dateCell.setCellValue(report.getReturnDate());
            dateCell.setCellStyle(dateStyle);
        }
    }

    /**
     * Запись на диск
     */
    private String writeFileToDisk(Workbook workbook, String pathToFile) throws IOException {
        String directory = RegexProcessing.getArrayAfterSplit("/", pathToReport)[0];
        FileProcessing.createDirectory(directory);

        while (FileProcessing.isExists(pathToFile)) {
            pathToFile = RegexProcessing.renameFileCount(pathToFile, directory);
        }

        FileOutputStream fileOutput = new FileOutputStream(pathToFile);
        workbook.write(fileOutput);
        fileOutput.close();

        return pathToFile;
    }
}
