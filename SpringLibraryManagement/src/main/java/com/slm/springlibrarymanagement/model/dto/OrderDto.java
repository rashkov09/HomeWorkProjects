package com.slm.springlibrarymanagement.model.dto;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.model.entities.Book;
import com.slm.springlibrarymanagement.model.entities.Client;

import java.time.LocalDate;

public class OrderDto extends BaseDto{
    private static final Integer DEFAULT_DUE_DATE_PERIOD = 1;
    private Client client;
    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Integer bookCount;
    private LocalDate stampModified;


    public OrderDto(Long id, Client client, Book book, LocalDate issueDate, Integer bookCount) {
        super(id);
        this.client = client;
        this.book = book;
        this.issueDate = issueDate;
        this.bookCount = bookCount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        setDueDate(issueDate);
        setStampModified(issueDate);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    private void setDueDate(LocalDate issueDate) {
        this.dueDate = issueDate.plusMonths(DEFAULT_DUE_DATE_PERIOD);
    }

    public LocalDate getStampModified() {
        return stampModified;
    }

    public void setStampModified(LocalDate stampModified) {
        this.stampModified = stampModified;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public String toString() {
        return String.format("Order ID: %d\nClient: %s\nBook: %s\nIssue date: %s\nDue date: %s\nBook count: %d\n",
                getId(), getClient().fullName(), getBook().getName(), getIssueDate(), getDueDate(), getBookCount());
    }

    public void extendDueDate(Integer count, IncreasePeriod period) {
        switch (period) {
            case DAY -> this.dueDate = dueDate.plusDays(count);
            case WEEK -> this.dueDate = dueDate.plusWeeks(count);
            case MONTH -> this.dueDate = dueDate.plusMonths(count);
        }
    }

    public void updateDueDate(LocalDate date) {
        this.dueDate = date;
    }

}
