package com.lma.cviews;

import com.lma.service.BookService;
import com.lma.service.impl.BookServiceImpl;
import com.lma.util.ConsoleRangeReader;
import com.lma.util.ConsoleReader;

import static com.lma.constatns.CustomExceptions.BOOK_NAME_INPUT_MESSAGE;
import static com.lma.constatns.CustomExceptions.DATE_INPUT_MESSAGE;

public class BookConsoleView implements ConsoleView {
    private final static ConsoleView mainConsoleView = new MainMenuView();
    public static final BookService bookService = new BookServiceImpl();
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 5;
    private static final String BOOKS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all books
                    2. Search for a book by name
                    3. Search for a book by publish date
                    4. Search for a book by author name
                    5. Add book
                                        
                    0. Back
                    """;
    private static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert author name: ";
    private static final String BOOK_ADD_SUCCESS_MESSAGE = "Book %s added successfully!\n";
    private static final String BOOK_ADD_UNSUCCESSFUL_MESSAGE = "Book was not added! Please, try again!";

    @Override
    public void showItemMenu() {
        System.out.println(BOOKS_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                mainConsoleView.showItemMenu();
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
                addBook();
                break;
        }
        showItemMenu();
    }

    private void printBookByAuthorName() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        System.out.println(bookService.getBooksByAuthorName(authorName));
    }

    private void printBookByIssueDate() {
        System.out.println(DATE_INPUT_MESSAGE);
        String issueDate = ConsoleReader.readString();
        System.out.println(bookService.getBooksByIssueDate(issueDate));

    }


    public void printAllBooks() {
        System.out.println(bookService.getAllBooks());
    }


    public void addBook() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        System.out.println(DATE_INPUT_MESSAGE);
        String date = ConsoleReader.readString();

        if (bookService.addBook(bookName, authorName, date)) {
            System.out.printf((BOOK_ADD_SUCCESS_MESSAGE), bookName);
        } else {
            System.out.println(BOOK_ADD_UNSUCCESSFUL_MESSAGE);
        }
    }

    public void printBookByName() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        System.out.println(bookService.getBookByName(bookName));
    }
}
