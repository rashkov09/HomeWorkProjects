package com.lma.service.impl;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.repository.AuthorRepository;
import com.lma.repository.impl.AuthorRepositoryImpl;
import com.lma.service.AuthorService;
import com.lma.service.BookService;

import java.util.NoSuchElementException;

import static com.lma.constatns.CustomExceptions.BOOK_NOT_FOUND_EXCEPTION;
import static com.lma.constatns.CustomExceptions.NO_SUCH_AUTHOR_EXCEPTION;

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
    public Boolean addAuthor(String authorName) {

      return authorRepository.addAuthor(new Author(authorName));

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
    public String findAuthorByName(String name) {
        return authorRepository.findAuthorByName(name).toString();
    }

    @Override
    public String findAuthorByBookName(String bookName) {
        try {
            Book book;
            try {
                book = bookService.getBook(bookName);
            }catch (NoSuchElementException e){
                return BOOK_NOT_FOUND_EXCEPTION;
            }
            return authorRepository.findAuthorByBookName(book).toString();
        }catch (NoSuchElementException e){
            return NO_SUCH_AUTHOR_EXCEPTION;
        }
    }

    @Override
    public Author getAuthor(String authorName) {
        return authorRepository.getAuthor(authorName);
    }
}
