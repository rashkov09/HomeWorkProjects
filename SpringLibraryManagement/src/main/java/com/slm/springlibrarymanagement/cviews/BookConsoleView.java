package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.exceptions.BackUpFailedException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.stereotype.Component;

@Component
public class BookConsoleView implements ConsoleView {
    public final BookService bookService;
    public final AuthorService authorService;
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 6;
    private static final String BOOKS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all books
                    2. Search for a book by name
                    3. Search for a book by publish date
                    4. Search for a book by author name
                    5. Search for books starting with
                    6. Add book
                                        
                    0. Back
                    """;
    private static final String AUTHOR_ID_SELECT_MESSAGE = "Please, insert author id from the list above: ";
    private static final String BOOK_NAME_INPUT_MESSAGE = "Please, insert book name: ";
    private static final String BOOK_ISSUE_DATE_MESSAGE = "Please, insert book issue date (dd/MM/yyyy): ";
    private static final String BOOK_NUMBER_OF_COPIES_MESSAGE = "Please, insert number of copies: ";

    public BookConsoleView(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(BOOKS_OPTION_MESSAGE);
        System.out.print("Please, choose an option: ");
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
                //   printBookByIssueDate();
                break;
            case 4:
                //  printBookByAuthorName();
                break;
            case 5:
                //   printBookStartingWith();
                break;
            case 6:
                addBook();
                break;
        }
        showItemMenu(invoker);
    }

    private void printBookByName() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        try {
            System.out.println(bookService.findBookByName(bookName));
        }catch (Exception e){
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
