package dev.library.form;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Класс для описания информации об отчёте для HTML-формы
 * @version 1.0
 */
public class ReportForm {
    /* идентификатор человека */
    private long personId;
    /* идентификатор книги */
    private long bookId;
    /* дата выдачи */
    private LocalDateTime issueDate;
    /* дата возврата */
    private LocalDateTime returnDate;

    /**
     * Конструктор для создания нового объекта типа ReportForm
     * @param personId - идентификатор человека
     * @param bookId - идентификатор книги
     * @param issueDate - дата выдачи
     * @param returnDate - дата возврата
     */
    public ReportForm(long personId, long bookId, LocalDateTime issueDate, LocalDateTime returnDate) {
        this.personId = personId;
        this.bookId = bookId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }
    /**
     * Конструктор по умолчанию для создания нового объекта типа ReportForm
     */
    public ReportForm() {}

    public long getPersonId() {
        return personId;
    }
    public void setPersonId(long personId) {
        this.personId = personId;
    }
    public long getBookId() {
        return bookId;
    }
    public void setBook(long bookId) {
        this.bookId = bookId;
    }
    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(",", this.getClass().getName() + "{", "}");
        String objectToString = joiner.add("personId=" + personId).add("bookId=" + bookId)
                .add("issueDate=" + issueDate).add("returnDate=" + returnDate).toString();
        return objectToString;
    }
}
