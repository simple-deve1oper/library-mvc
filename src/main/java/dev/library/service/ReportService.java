package dev.library.service;

import dev.library.dao.impl.ReportDAO;
import dev.library.model.Book;
import dev.library.model.Person;
import dev.library.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportDAO reportDAO;

    /**
     * Добавление отчета
     * @param report - отчёт
     */
    public void addReport(Report report) {
        reportDAO.create(report);
    }
    /**
     * Получение отчёта по идентификатору
     * @param id - идентификатор
     * @return класс-оболочка Optional типа Report
     */
    public Optional<Report> getReport(long id) {
        Optional<Report> optional = reportDAO.read(id);
        return optional;
    }
    /**
     * Получение всех отчётов
     * @return список объектов типа Report
     */
    public List<Report> getAllReports() {
        List<Report> reports = reportDAO.readAll();
        return reports;
    }
    /**
     * Обновление информации об отчёте
     * @return результат обновления информации об отчёте
     */
    public boolean updateInfo(Report report, long id) {
        if (reportDAO.read(id).isEmpty()) {
            return false;
        }
        reportDAO.update(report, id);
        return true;
    }
    /**
     * Поиск отчёта, в котором книга не возвращена в библиотеку
     * @param book - книга
     * @return класс-оболочка Optional типа Report
     */
    public Optional<Report> findIssuedBook(Book book) {
        Optional<Report> optionalReport = reportDAO.findIssuedBook(book);
        return optionalReport;
    }
    /**
     * Поиск отчётов по человеку, который не вернул книги в библиотеку
     * @param person - человек
     * @return список объектов типа Report
     */
    public List<Report> findIssuedBooks(Person person) {
        List<Report> reports = reportDAO.findIssuedBooks(person);
        return reports;
    }
}
