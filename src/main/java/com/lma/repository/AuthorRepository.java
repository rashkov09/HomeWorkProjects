package com.lma.repository;

import com.lma.model.Author;

import java.util.HashSet;

public interface AuthorRepository {

    void loadAuthorData();
    Boolean addAuthor(Author author);
    HashSet<Author> getAllAuthors();


    Author findAuthorByName(String name);


}
