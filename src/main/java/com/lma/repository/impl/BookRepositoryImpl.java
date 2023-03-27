package com.lma.repository.impl;

import com.lma.model.Book;
import com.lma.repository.BookRepository;
import com.lma.util.BookFileAccessor;
import com.lma.util.BookMapper;
import com.lma.util.LocalDateFormatter;

import java.io.IOException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class BookRepositoryImpl implements BookRepository {
    private final static BookFileAccessor bookFileAccessor = new BookFileAccessor();
    private final static HashSet<Book> bookList = new HashSet<>();


    @Override
    public void loadBookData() {
        bookFileAccessor.readAllLines().forEach(line -> bookList.add(BookMapper.mapBookFromString(line)));
    }

    @Override
    public Set<Book> findBooksByAuthorName(String authorName) {
        return bookList.stream().filter(book -> book.getAuthor().getName().equals(authorName)).collect(Collectors.toSet());
    }

    @Override
    public Book findBookByName(String bookName) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getName().equals(bookName)).findFirst().orElseThrow();
    }

    @Override
    public Set<Book> findBooksByIssueDate(String publishDate) {
        return bookList.stream().filter(book -> book.getPublishDate().equals(LocalDateFormatter.stringToLocalDate(publishDate))).collect(Collectors.toSet());
    }

    @Override
    public HashSet<Book> getAllBooks() {
        return bookList;
    }

    @Override
    public Boolean addBook(Book book) {
        if (bookList.add(book)) {
            try {
                bookFileAccessor.writeLine(BookMapper.mapBookToString(book));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

}
