package com.slm.springlibrarymanagement.model.entities;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@SequenceGenerator(name = "default_gen", sequenceName = "orders_seq", allocationSize = 1)
public class Order extends BaseEntity {
    private Client client;

    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Integer bookCount;
    private LocalDate stampModified;

    private static final Integer DEFAULT_DUE_DATE_PERIOD = 1;

    public Order() {
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Column(nullable = false)
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        setStampModified(issueDate);
    }

    @Column(nullable = false)
    public LocalDate getDueDate() {
        return dueDate;
    }


    private void setDueDate(LocalDate issueDate) {
        this.dueDate = issueDate.plusMonths(DEFAULT_DUE_DATE_PERIOD);
    }

    @Nullable
    public LocalDate getStampModified() {
        return stampModified;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public void setStampModified(LocalDate stampModified) {
        this.stampModified = stampModified;
    }
}