package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.model.entities.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAllAuthors() throws NoEntriesFoundException;

    String insertAuthor(String author) throws InvalidAuthorNameException, AuthorAlreadyExistsException;

    void backupToFile() throws BackUpFailedException;

    Author findAuthorByName(String authorName) throws InvalidAuthorNameException, AuthorNotFoundException;

    AuthorDto findAuthorById(String authorId) throws InvalidIdException, AuthorNotFoundException;


    void loadAuthorData() throws SQLException, InvalidIdException;
}
