package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;


public class Order extends BaseEntity {
    private static final Integer DEFAULT_DUE_DATE_PERIOD = 1;
    private Client client;
    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Integer bookCount;
    private LocalDate stampModified;

    public Order() {
    }

    @ManyToOne
    @JoinColumn(name = "client")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne
    @JoinColumn(name = "book")
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
                getId(),getClient().fullName(),getBook().getName(),getIssueDate(),getDueDate(),getBookCount());
    }
}
