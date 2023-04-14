package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.slm.springlibrarymanagement.constants.messages.BookMessages.*;
import static com.slm.springlibrarymanagement.constants.messages.GeneralMessages.CHOOSE_AN_OPTION_MESSAGE;

@Component
public class BookConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 6;
    public final BookService bookService;
    public final AuthorService authorService;

    @Autowired
    public BookConsoleView(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(BOOKS_OPTION_MESSAGE);
        System.out.println(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                invoker.showItemMenu(this);
                break;
            case 1:
                printAllBooks();
                break;
            case 2:
                printBookByName();
                break;
            case 3:
                printBookByIssueDate();
                break;
            case 4:
                printBookByAuthorName();
                break;
            case 5:
                printBookStartingWith();
                break;
            case 6:
                addBook();
                break;
        }
        showItemMenu(invoker);
    }

    private void printBookStartingWith() {
        System.out.println(QUERY_NAME_PREFIX);
        String prefix = ConsoleReader.readString();
        try {
            System.out.println(bookService.findBooksByNameStartingWith(prefix));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printBookByAuthorName() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        try {
            System.out.println(bookService.findBooksByAuthorName(authorName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printBookByIssueDate() {
        System.out.println(BOOK_ISSUE_DATE_SEARCH_MESSAGE);
        String issueDate = ConsoleReader.readString();
        try {
            System.out.println(bookService.findBookByIssueDate(issueDate).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printBookByName() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        try {
            System.out.println(bookService.findBookByName(bookName).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBook() {
        try {
            System.out.println(authorService.findAllAuthors());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(AUTHOR_ID_SELECT_MESSAGE);
        String authorId = ConsoleReader.readString();
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        System.out.println(BOOK_ISSUE_DATE_MESSAGE);
        String issueDate = ConsoleReader.readString();
        System.out.println(BOOK_NUMBER_OF_COPIES_MESSAGE);
        String numberOfCopies = ConsoleReader.readString();
        try {
            System.out.println(bookService.insertBook(authorId, bookName, issueDate, numberOfCopies));
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }
    }

    private void printAllBooks() {
        try {
            System.out.println(bookService.findAllBooks());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
