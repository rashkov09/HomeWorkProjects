package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {
    private static final Integer DEFAULT_NUMBER_OF_COPIES = 0;
    private String name;
    private Author author;
    private LocalDate localeDate;
    private Integer numberOfCopies;


    public Book() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Column(nullable = false)
    public LocalDate getLocaleDate() {
        return localeDate;
    }

    public void setLocaleDate(LocalDate localeDate) {
        this.localeDate = localeDate;
    }

    @Column(nullable = false)
    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        if (numberOfCopies == null || numberOfCopies <DEFAULT_NUMBER_OF_COPIES ){
            this.numberOfCopies = DEFAULT_NUMBER_OF_COPIES;
        } else {
            this.numberOfCopies = numberOfCopies;
        }
    }

    public void addCopies(Integer numberOfCopies){
        setNumberOfCopies(getNumberOfCopies()+numberOfCopies);
    }

}
