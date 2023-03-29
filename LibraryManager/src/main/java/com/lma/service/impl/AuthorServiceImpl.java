package com.lma.service.impl;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.repository.AuthorRepository;
import com.lma.repository.impl.AuthorRepositoryImpl;
import com.lma.service.AuthorService;
import com.lma.service.BookService;

import java.util.NoSuchElementException;

import static com.lma.constants.CustomMessages.*;

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
    public String addAuthor(String authorName) {
        if (authorName.isEmpty()) {
            return INVALID_AUTHOR_MESSAGE;
        }
        if (authorRepository.addAuthor(new Author(authorName))) {
            return String.format(AUTHOR_ADDED_SUCCESSFULLY_MESSAGE, authorName);
        }
        return String.format(FAILED_TO_ADD_AUTHOR, authorName);
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
        return builder.isEmpty() ? EMPTY_RESULT_MESSAGE : builder.toString();
    }

    @Override
    public String findAuthorByName(String name) {
        if (name.isEmpty()) {
            return INVALID_AUTHOR_MESSAGE;
        }
        return authorRepository.findAuthorByName(name).toString();
    }

    @Override
    public String findAuthorByBookName(String bookName) {
        try {
            Book book;
            try {
                book = bookService.getBook(bookName);
            } catch (NoSuchElementException e) {
                return BOOK_NOT_FOUND_EXCEPTION;
            }
            return authorRepository.findAuthorByBookName(book).toString();
        } catch (NoSuchElementException e) {
            return NO_SUCH_AUTHOR_EXCEPTION;
        }
    }

    @Override
    public Author getAuthor(String authorName) {
        return authorRepository.getAuthor(authorName);
    }
}
