package com.lma.repository.impl;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.repository.AuthorRepository;
import com.lma.accessor.AuthorFileAccessor;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class AuthorRepositoryImpl implements AuthorRepository {
    private final static AuthorFileAccessor authorFileAccessor = new AuthorFileAccessor();
    private final static Set<Author> authorList = new HashSet<>();


    @Override
    public void loadAuthorData() {
        authorFileAccessor.readAllLines().forEach(line -> {
            Author author = new Author(line);
            authorList.add(author);
        });
    }

    @Override
    public Set<Author> getAllAuthors() {
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
    public Author findAuthorByName(String name) throws NoSuchElementException {
        return authorList.stream().filter(author -> author.getName().equalsIgnoreCase(name)).findFirst().orElseThrow();
    }

    @Override
    public Author findAuthorByBookName(Book book) throws NoSuchElementException {
        return authorList.stream().filter(author -> author.getBooks().contains(book)).findFirst().orElseThrow();
    }

    @Override
    public Author getAuthor(String authorName) {
        return authorList.stream().filter(author -> author.getName().equals(authorName)).findFirst().orElse(null);
    }
}
