package com.lma.model;

import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private Client client;
    private Book book;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Order(Client client, Book book, LocalDate issueDate, LocalDate dueDate) {
        this.client = client;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Client getClient() {
        return client;
    }

    public Book getBook() {
        return book;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getClient().equals(order.getClient()) && getBook().equals(order.getBook());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), getBook());
    }

    @Override
    public String toString() {
        return String.format("""
                                  Client name: %s
                                  Book details:
                                                Book name: %s
                                                Author name: %s
                                                Published on: %s
                                 Order issue date: %s
                                 Order due date: %s
                """, this.client.getFullName(),
                this.book.getName(),
                this.book.getAuthor().getName(),
                this.book.getPublishDate(),
                this.issueDate,
                this.dueDate);
    }
}
