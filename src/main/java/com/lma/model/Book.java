package com.lma.model;

import java.time.LocalDate;
import java.util.Objects;

public class Book implements Comparable<Book> {
    private String name;
    private Author author;
    private LocalDate publishDate;

    public Book(String name, Author author, LocalDate publishDate) {
         setName(name);
         setAuthor(author);
         setPublishDate(publishDate);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    private void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    private void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getName().equals(book.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return String.format("Book Name: %s\nAuthor Name: %s\nPublish Date: %s\n", this.name,this.author.getName(),this.publishDate.toString());
    }

    @Override
    public int compareTo(Book o) {
        return this.name.compareTo(o.getName());
    }
}
