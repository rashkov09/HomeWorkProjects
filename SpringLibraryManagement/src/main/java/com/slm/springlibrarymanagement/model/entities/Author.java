package com.slm.springlibrarymanagement.model.entities;

import jakarta.persistence.*;

import java.util.Objects;
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

    @OneToMany(mappedBy = "author")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return getName().equals(author.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
