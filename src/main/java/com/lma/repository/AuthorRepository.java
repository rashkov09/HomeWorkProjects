package com.lma.repository;

import com.lma.model.Author;
import com.lma.model.Book;

import java.util.HashSet;
import java.util.Set;

public interface AuthorRepository {

    void loadAuthorData();
    Boolean addAuthor(Author author);
    Set<Author> getAllAuthors();

    Author findAuthorByName(String name);

    Author findAuthorByBookName(Book book);

    Author getAuthor(String authorName);
}
