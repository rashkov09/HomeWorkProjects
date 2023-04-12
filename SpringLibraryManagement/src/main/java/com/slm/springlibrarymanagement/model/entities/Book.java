package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;


public class Book extends BaseEntity {
    private static final Integer DEFAULT_NUMBER_OF_COPIES = 1;
    private String name;

    private Author author;
    private LocalDate issueDate;
    private Integer numberOfCopies;




    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "id")
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
        if (numberOfCopies == null || numberOfCopies < DEFAULT_NUMBER_OF_COPIES) {
            this.numberOfCopies = DEFAULT_NUMBER_OF_COPIES;
        } else {
            this.numberOfCopies = numberOfCopies;
        }
    }



    public void addCopies(Integer numberOfCopies) {
        setNumberOfCopies(getNumberOfCopies() + numberOfCopies);
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", issueDate=" + issueDate +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}
