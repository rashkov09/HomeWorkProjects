package com.lma.service.impl;

import com.lma.model.Author;
import com.lma.model.Book;
import com.lma.repository.BookRepository;
import com.lma.repository.impl.BookRepositoryImpl;
import com.lma.service.AuthorService;
import com.lma.service.BookService;
import com.lma.util.LocalDateFormatter;

import java.util.Set;

public class BookServiceImpl implements BookService {
    private final static AuthorService authorService = new AuthorServiceImpl();
    private final static BookRepository bookRepository = new BookRepositoryImpl();


    public BookServiceImpl() {
        seedBooks();
    }

    @Override
    public void seedBooks() {
        bookRepository.loadBookData();
    }

    @Override
    public void addBook(String name, String authorName, String publishDate) {
        // TODO to validate user input
      Book book = new Book(name,new Author(authorName), LocalDateFormatter.stringToLocalDate(publishDate));
        bookRepository.addBook(book);
        Author existingAuthor = authorService.findAuthorByName(authorName);

        if (existingAuthor != null){
            existingAuthor.addBook(book);
        }
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
    public Book getBookByName(String bookName) {
        return bookRepository.findBookByName(bookName);
    }
}
