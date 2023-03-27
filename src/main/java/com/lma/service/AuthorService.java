package com.lma.service;

import com.lma.model.Author;

public interface AuthorService {

    void seedAuthors();
    Boolean addAuthor(String authorName);
    String getAllAuthors();

    String findAuthorByName(String name);

    String findAuthorByBookName(String authorName);

    Author getAuthor(String authorName);
}
