package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_VIEW_TEMPLATE = "| %s |\n";
    private final AuthorRepository authorRepository;
    private final InputValidator inputValidator;


    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, InputValidator inputValidator) {
        this.authorRepository = authorRepository;
        this.inputValidator = inputValidator;

    }


    @Override
    public String findAllAuthors() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> builder.append(String.format(AUTHORS_VIEW_TEMPLATE, author.toString())));
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }

    @Override
    public String insertAuthor(String authorName) throws InvalidAuthorNameException, AuthorAlreadyExistsException {
        if (inputValidator.isNotValidFullName(authorName)) {
            throw new InvalidAuthorNameException();
        }
        Author author = new Author();

        author.setName(formatAuthorName(authorName.toLowerCase()));

        try {
            author = findAuthorByName(authorName);
        } catch (AuthorNotFoundException e) {
            if (authorRepository.addAuthor(author)) {
                return String.format("Author %s added successfully!", author.getName());
            }
        }
        return "Something went wrong! Please, try again";
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        authorRepository.writeDataToFile();
    }

    private String formatAuthorName(String authorName) {
        StringBuilder builder = new StringBuilder();
        String[] names = authorName.split("\\s");
        for (int i = 0; i < names.length; i++) {
            if (i == names.length - 1) {
                builder.append(transformString(names[i]));
            } else {
                builder.append(transformString(names[i])).append(" ");
            }
        }

        return builder.toString();
    }

    private String transformString(String name) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String restOfName = name.substring(1);

        return String.format("%s%s", firstLetter, restOfName);
    }

    @Override
    public Author findAuthorByName(String authorName) throws InvalidAuthorNameException, AuthorNotFoundException {
        if (inputValidator.isNotValidFullName(authorName)) {
            throw new InvalidAuthorNameException();
        }
        try {
            return authorRepository.findByName(authorName);
        } catch (NoSuchElementException e) {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public Author findAuthorById(String authorId) throws InvalidIdException, AuthorNotFoundException {
        try {
            Long.parseLong(authorId);
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        }
        try {
            return authorRepository.findById(Long.parseLong(authorId));
        } catch (NoSuchElementException e) {
            throw new AuthorNotFoundException();
        }
    }

    @Override
    public void loadAuthorData() throws SQLException, InvalidIdException {
        authorRepository.loadAuthors();
    }

}
