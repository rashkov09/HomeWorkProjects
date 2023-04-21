package com.slm.springlibrarymanagement.controller.request;

import com.slm.springlibrarymanagement.model.entities.Author;

import java.time.LocalDate;

public class BookRequest {
    private String name;
    private Author author;
    private LocalDate issueDate;
    private Integer numberOfCopies;

    public BookRequest() {
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }
}
