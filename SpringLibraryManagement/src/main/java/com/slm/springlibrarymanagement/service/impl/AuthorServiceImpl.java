package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.accessor.AuthorFileAccessor;
import com.slm.springlibrarymanagement.model.entities.Author;
import com.slm.springlibrarymanagement.repository.AuthorRepository;
import com.slm.springlibrarymanagement.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public String findAllAuthors() {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> {
            builder.append(String.format(AUTHORS_VIEW_TEMPLATE, author.toString()));
        });

        return builder.toString();
    }

    @Override
    public String insertAuthor(String authorName) {
        Author author = new Author();
        author.setName(authorName);
        return String.valueOf(authorRepository.save(author));
    }

    @Override
    public void importAuthors() {
        if (findAllAuthors().isEmpty()) {
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

            } catch (Exception e) {
                System.out.println("File for authors not found");
            }
            authorRepository.saveAll(authorsList);
        }
    }

    @Override
    public void backupToFile() {
        StringBuilder builder = new StringBuilder();
        authorRepository.findAll().forEach(author -> {
            builder
                    .append(String.format("%d. %s",author.getId(),author.getName()))
                    .append("\n");
        });

        try {
            authorFileAccessor.writeLine(builder.toString().trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
