package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authors")
@SequenceGenerator(name = "default_gen", sequenceName = "authors_seq", allocationSize = 1)
public class Author extends BaseEntity {
    private String name;
    private Set<Book> books;

    public Author() {
    }


    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
