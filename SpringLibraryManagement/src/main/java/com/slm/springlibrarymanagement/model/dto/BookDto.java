package com.slm.springlibrarymanagement.model.dto;

import com.slm.springlibrarymanagement.model.entities.Author;

import java.time.LocalDate;

public class BookDto extends BaseDto {

    private String name;
    private Author author;
    private LocalDate issueDate;
    private Integer numberOfCopies;


    public BookDto(Long id, String name, Author author, LocalDate issueDate, Integer numberOfCopies) {
        super(id);
        this.name = name;
        this.author = author;
        this.issueDate = issueDate;
        this.numberOfCopies = numberOfCopies;
    }

    public BookDto(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
