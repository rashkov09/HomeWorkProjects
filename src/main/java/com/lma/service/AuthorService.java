package com.lma.service;

import com.lma.model.Author;

public interface AuthorService {

    void seedAuthors();
    void addAuthor(String authorName);
    String getAllAuthors();

    Author findAuthorByName(String name);
}
