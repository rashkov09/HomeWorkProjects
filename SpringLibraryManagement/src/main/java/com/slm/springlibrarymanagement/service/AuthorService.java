package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.model.entities.Author;

public interface AuthorService {

    String findAllAuthors() throws NoEntriesFoundException;

    String insertAuthor(String author) throws InvalidAuthorNameException, AuthorAlreadyExistsException;

    void importAuthors() throws FileForEntityNotFound;

    void backupToFile() throws BackUpFailedException;

    Author findAuthorByName(String authorName) throws InvalidAuthorNameException, AuthorNotFoundException;

    Author findAuthorById(String authorId) throws InvalidIdException, AuthorNotFoundException;
}
