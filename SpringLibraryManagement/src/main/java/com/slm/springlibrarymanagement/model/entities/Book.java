package com.slm.springlibrarymanagement.model.entities;


import java.time.LocalDate;


public class Book extends BaseEntity implements Comparable<Book> {
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

    public void removeBooks(Integer bookCount) {
        setNumberOfCopies(this.numberOfCopies - bookCount);
    }

    public void addCopies(Integer numberOfCopies) {
        setNumberOfCopies(getNumberOfCopies() + numberOfCopies);
    }

    @Override
    public String toString() {
        return String.format("""
                --------------------------------------------------
                | Book ID: %d
                | Book name: %s
                | Written by: %s
                | Issued on: %s
                | Copies: %d
                --------------------------------------------------
                """, this.getId(), this.getName(), this.author.getName(), this.issueDate, this.getNumberOfCopies());
    }

    @Override
    public int compareTo(Book o) {
        return this.getId().compareTo(o.getId());
    }
}
