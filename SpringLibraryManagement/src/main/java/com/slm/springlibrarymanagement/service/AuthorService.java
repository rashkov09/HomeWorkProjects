package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.controller.request.AuthorRequest;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.model.dto.AuthorDto;
import com.slm.springlibrarymanagement.model.entities.Author;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAllAuthors();

    Author insertAuthor(AuthorRequest authorRequest) throws InvalidAuthorNameException, AuthorAlreadyExistsException;

    void backupToFile() throws BackUpFailedException;

    Author findAuthorByName(String authorName) throws InvalidAuthorNameException, AuthorNotFoundException;

    AuthorDto findAuthorById(String authorId);


}
