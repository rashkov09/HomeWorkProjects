package com.lma.repository.impl;

import com.lma.model.Author;
import com.lma.repository.AuthorRepository;
import com.lma.util.AuthorFileAccessor;

import java.io.IOException;
import java.util.HashSet;

public class AuthorRepositoryImpl implements AuthorRepository {
    private final static AuthorFileAccessor authorFileAccessor = new AuthorFileAccessor();
    private final static HashSet<Author> authorList = new HashSet<>();


    @Override
    public void loadAuthorData() {
        authorFileAccessor.readAllLines().forEach(line -> {
            Author author = new Author(line);
            authorList.add(author);
        });
    }

    @Override
    public HashSet<Author> getAllAuthors() {
        return authorList;
    }

    @Override
    public Boolean addAuthor(Author author) {

        try {
            if (authorList.add(author)) {
                authorFileAccessor.writeLine(author.getName());
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public Author findAuthorByName(String name) {
        return authorList.stream().filter(author -> author.getName().equals(name)).findFirst().orElse(null);
    }
}
