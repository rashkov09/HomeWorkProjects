package com.lma.service.impl;

import com.lma.model.Author;
import com.lma.repository.AuthorRepository;
import com.lma.repository.impl.AuthorRepositoryImpl;
import com.lma.service.AuthorService;
import com.lma.service.BookService;

public class AuthorServiceImpl implements AuthorService {

    private final static AuthorRepository authorRepository = new AuthorRepositoryImpl();
    private final static BookService bookService = new BookServiceImpl();


    public AuthorServiceImpl() {
        seedAuthors();
    }


    @Override
    public void seedAuthors() {
        authorRepository.loadAuthorData();
    }

    @Override
    public void addAuthor(String authorName) {
        // TODO to validate user input
        authorRepository.addAuthor(new Author(authorName));
    }

    @Override
    public String getAllAuthors() {
        StringBuilder builder = new StringBuilder();
        authorRepository.getAllAuthors().forEach(author -> {
                    bookService.findBooksByAuthorName(author.getName()).forEach(author::addBook);
                    builder
                            .append(author)
                            .append("\n");
                }
        );
        return builder.toString();
    }

    @Override
    public Author findAuthorByName(String name) {
        return authorRepository.findAuthorByName(name);
    }
}
