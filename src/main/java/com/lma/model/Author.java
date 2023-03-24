package com.lma.model;

import java.util.HashSet;
import java.util.Objects;

public class Author {
    private String name;
    private HashSet<Book> books;

    public Author(String name) {
        this.name = name;
        this.books = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append(String.format("Author: %s\nBooks:\n",this.name));
      books.forEach(book -> {
          builder.append("\t\t -- ").append(book.getName()).append("\n");
      });
      return builder.toString();
    }

    public void addBook(Book book) {
        this.books.add(book);
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
