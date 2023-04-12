package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.FileForEntityNotFound;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_VIEW_TEMPLATE = "| %s |\n";
    private final AuthorRepository authorRepository;
    private final AuthorFileAccessor authorFileAccessor;
    private final InputValidator inputValidator;


    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorFileAccessor authorFileAccessor, InputValidator inputValidator) {
        this.authorRepository = authorRepository;
        this.authorFileAccessor = authorFileAccessor;
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
            if(authorRepository.addAuthor(author)) {
             return   String.format("Author %s added successfully!", author.getName());
            }
        }
        return  "Something went wrong! Please, try again";
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
    public void importAuthors() throws FileForEntityNotFound {
        try {
            findAllAuthors();
        } catch (NoEntriesFoundException e) {
            List<Author> authorsList = new ArrayList<>();
            try {
                authorFileAccessor.readAllLines().forEach(line -> {
                    Author author = new Author();
                    if (line.contains(".")) {
                        String[] splitData = line.split("\\.\\s", 2);
                        author.setName(splitData[1]);
                    } else {
                        author.setName(line);
                    }
                    authorsList.add(author);
                });

            } catch (Exception ex) {
                throw new FileForEntityNotFound();
            }
            authorRepository.addAll(authorsList);
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> builder
                .append(String.format("%d. %s", author.getId(), author.getName()))
                .append("\n"));

        try {
            authorFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new BackUpFailedException();
        }
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
        Author author;
        try {

            author = authorRepository.findById(Long.parseLong(authorId));
            if (author == null) {
                throw new AuthorNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        } catch (EmptyResultDataAccessException s) {
            throw new AuthorNotFoundException();
        }
        return author;
    }

    @Override
    public void loadAuthorData() throws SQLException, InvalidIdException {
        authorRepository.loadAuthors();
    }

}
