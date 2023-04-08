package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.exceptions.*;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorFileAccessor authorFileAccessor;

    private static final String AUTHORS_VIEW_TEMPLATE = "| %s |\n";


    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorFileAccessor authorFileAccessor) {
        this.authorRepository = authorRepository;
        this.authorFileAccessor = authorFileAccessor;
    }


    @Override
    public String findAllAuthors() throws NoEntriesFoundException {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> {
            builder.append(String.format(AUTHORS_VIEW_TEMPLATE, author.toString()));
        });
        if (builder.toString().isEmpty() || builder.toString().isBlank()) {
            throw new NoEntriesFoundException();
        }

        return builder.toString();
    }

    @Override
    public String insertAuthor(String authorName) throws InvalidAuthorNameException, AuthorAlreadyExistsException {
        String result = "";
        if (isValidAuthorName(authorName)) {
            throw new InvalidAuthorNameException();
        }
        Author author = new Author();

        author.setName(formatAuthorName(authorName.toLowerCase()));

        try {
            author = findAuthorByName(authorName);
            if (author != null) {
                throw new AuthorAlreadyExistsException();
            }
        } catch (AuthorNotFoundException e) {
            authorRepository.save(author);
            result = String.format("Author %s added successfully!", author.getName());
        }
        return !result.isEmpty() ? result : "Something went wrong! Please, try again";
    }

    private String formatAuthorName(String authorName) {
        String[] names = authorName.split("\\s");
        String firstName= transformString(names[0]);
        String lastName = transformString(names[1]);
        return String.format("%s %s",firstName,lastName);
    }

    private String transformString(String name) {
        String firstLetter = name.substring(0,1).toUpperCase();
        String restOfName = name.substring(1);

        return String.format("%s%s",firstLetter,restOfName);
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
            authorRepository.saveAll(authorsList);
        }
    }

    @Override
    public void backupToFile() throws BackUpFailedException {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> {
            builder
                    .append(String.format("%d. %s", author.getId(), author.getName()))
                    .append("\n");
        });

        try {
            authorFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new BackUpFailedException();
        }
    }

    @Override
    public Author findAuthorByName(String authorName) throws InvalidAuthorNameException, AuthorNotFoundException {
        if (isValidAuthorName(authorName)) {
            throw new InvalidAuthorNameException();
        }
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            throw new AuthorNotFoundException();
        }
        return author;
    }

    @Override
    public Author findAuthorById(String authorId) throws InvalidIdException, AuthorNotFoundException {
        Author author;
        try {
            Long id = Long.parseLong(authorId);
            author = authorRepository.findById(id).orElse(null);
            if (author == null) {
                throw new AuthorNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidIdException();
        }
        return author;
    }

    private boolean isValidAuthorName(String authorName) {
        String regex = "^[a-z-A-Z.+]+ [A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorName);
        return !matcher.matches();
    }

}
