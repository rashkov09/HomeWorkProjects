package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.model.entities.Author;

import java.util.Set;

public interface AuthorService {

    String findAllAuthors();

    String insertAuthor(String author);

    void importAuthors();

    void backupToFile();
}
