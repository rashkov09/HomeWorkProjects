package com.lma.repository.impl;

import com.lma.accessor.BookFileAccessor;
import com.lma.mapper.BookMapper;
import com.lma.model.Book;
import com.lma.repository.BookRepository;
import com.lma.util.DateInputValidator;
import com.lma.util.LocalDateFormatter;

import java.io.IOException;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static com.lma.constants.CustomMessages.FILE_NOT_FOUND_MESSAGE;
import static com.lma.constants.Paths.BOOKS_FILE_PATH;

public class BookRepositoryImpl implements BookRepository {
    private final static BookFileAccessor bookFileAccessor = new BookFileAccessor();
    private final static TreeSet<Book> bookList = new TreeSet<>();


    @Override
    public void loadBookData() {
        try {
            bookFileAccessor.readAllLines().forEach(line -> bookList.add(BookMapper.mapBookFromString(line)));
        }catch (Exception e){
            System.out.printf(FILE_NOT_FOUND_MESSAGE,BOOKS_FILE_PATH);
        }
    }

    @Override
    public Set<Book> findBooksByAuthorName(String authorName) {
        return bookList.stream().filter(book -> book.getAuthor().getName().equals(authorName)).collect(Collectors.toSet());
    }

    @Override
    public Book findBookByName(String bookName) throws NoSuchElementException {
        return bookList.stream().filter(book -> book.getName().equalsIgnoreCase(bookName)).findFirst().orElseThrow();
    }

    @Override
    public Set<Book> findBooksByIssueDate(String publishDate) {
        if (DateInputValidator.validate(publishDate)) {
            return bookList.stream().filter(book -> book.getPublishDate().equals(LocalDateFormatter.stringToLocalDate(publishDate))).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    @Override
    public boolean removeBook(Book book) {
        return bookList.remove(book);

    }

    @Override
    public Set<Book> getAllBooks() {
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
