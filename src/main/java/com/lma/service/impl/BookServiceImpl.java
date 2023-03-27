package com.lma.service.impl;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.repository.BookRepository;
import com.lma.repository.impl.BookRepositoryImpl;
import com.lma.service.AuthorService;
import com.lma.service.BookService;
import com.lma.util.LocalDateFormatter;

import java.util.NoSuchElementException;
import java.util.Set;

import static com.lma.constatns.CustomMessages.BOOK_NOT_FOUND_EXCEPTION;

public class BookServiceImpl implements BookService {
    private final static AuthorService authorService = new AuthorServiceImpl();
    private final static BookRepository bookRepository = new BookRepositoryImpl();
    private static final String NO_BOOKS_FOUND_MESSAGE = "No books written by %s found!\n";
    private static final String NO_BOOKS_FOUND_FOR_DATE_MESSAGE = "No books published on %s found!\n";

    public BookServiceImpl() {
        seedBooks();
    }

    @Override
    public void seedBooks() {
        bookRepository.loadBookData();
    }

    @Override
    public Boolean addBook(String name, String authorName, String publishDate) {
        authorService.addAuthor(authorName);
        Author existingAuthor = authorService.getAuthor(authorName);
        Book book = new Book(name, existingAuthor, LocalDateFormatter.stringToLocalDate(publishDate));
        existingAuthor.addBook(book);
        return bookRepository.addBook(book);
    }

    @Override
    public Book getBook(String name) {
        return bookRepository.findBookByName(name);
    }


    @Override
    public String getAllBooks() {
        StringBuilder builder = new StringBuilder();
        bookRepository.getAllBooks().forEach(book -> builder.append(book.toString()).append("\n"));
        return builder.toString();
    }

    @Override
    public Set<Book> findBooksByAuthorName(String authorName) {
        return bookRepository.findBooksByAuthorName(authorName);
    }

    @Override
    public String getBookByName(String bookName) {
        try {
            return bookRepository.findBookByName(bookName).toString();
        } catch (NoSuchElementException e) {
            return BOOK_NOT_FOUND_EXCEPTION;
        }
    }

    @Override
    public String getBooksByAuthorName(String authorName) {
        StringBuilder builder = new StringBuilder();
             bookRepository.findBooksByAuthorName(authorName).forEach(book ->
                     builder
                             .append(book)
                             .append("\n")
             );

             return builder.isEmpty() ? String.format(NO_BOOKS_FOUND_MESSAGE,authorName) : builder.toString();
        }

    @Override
    public String getBooksByIssueDate(String publishDate) {
        StringBuilder builder = new StringBuilder();

        bookRepository.findBooksByIssueDate(publishDate).forEach(book -> {
            builder
                    .append(book)
                    .append("\n");
        });
        return builder.isEmpty() ? String.format(NO_BOOKS_FOUND_FOR_DATE_MESSAGE,publishDate) : builder.toString() ;
    }
}
