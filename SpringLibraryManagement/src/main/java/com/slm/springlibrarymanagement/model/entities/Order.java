package com.slm.springlibrarymanagement.model.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
@SequenceGenerator(name = "default_gen", sequenceName = "orders_seq", allocationSize = 1)
public class Order extends BaseEntity{
    private Client client;
    private Set<Book> books;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime stampModified;

    public Order() {
    }
    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    @ManyToMany
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Column(nullable = false)
    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    @Column(nullable = false)
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    @Nullable
    public LocalDateTime getStampModified() {
        return stampModified;
    }

    public void setStampModified(LocalDateTime stampModified) {
        this.stampModified = stampModified;
    }
}
