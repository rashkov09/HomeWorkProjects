package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.service.BookService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import org.springframework.stereotype.Component;

@Component
public class BookConsoleView implements ConsoleView {
    public final BookService bookService;
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
    private static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert author name: ";
    private static final String SEARCH_VALUE_MESSAGE = "Please, insert query: ";

    public BookConsoleView(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(BOOKS_OPTION_MESSAGE);
//        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                invoker.showItemMenu(this);
                break;
            case 1:
//                printAllBooks();
                break;
//            case 2:
//                printBookByName();
//                break;
//            case 3:
//                printBookByIssueDate();
//                break;
//            case 4:
//                printBookByAuthorName();
//                break;
//            case 5:
//                printBookStartingWith();
//                break;
//            case 6:
//                addBook();
//                break;
//        }
//        showItemMenu();
        }

//    private void printBookStartingWith() {
//        System.out.println(SEARCH_VALUE_MESSAGE);
//        String searchValue = ConsoleReader.readString();
//        System.out.println(bookService.findBooksStartingWith(searchValue));
//    }
//
//    private void printBookByAuthorName() {
//        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
//        String authorName = ConsoleReader.readString();
//        System.out.println(bookService.getBooksByAuthorName(authorName));
//    }
//
//    private void printBookByIssueDate() {
//        System.out.println(DATE_INPUT_MESSAGE);
//        String issueDate = ConsoleReader.readString();
//        System.out.println(bookService.getBooksByIssueDate(issueDate));
//
//    }
//
//
//    public void printAllBooks() {
//        System.out.println(bookService.getAllBooks());
//    }
//
//
//    public void addBook() {
//        System.out.println(BOOK_NAME_INPUT_MESSAGE);
//        String bookName = ConsoleReader.readString();
//        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
//        String authorName = ConsoleReader.readString();
//        System.out.println(DATE_INPUT_MESSAGE);
//        String date = ConsoleReader.readString();
//
//       System.out.println(bookService.addBook(bookName, authorName, date));
//
//    }
//
//    public void printBookByName() {
//        System.out.println(BOOK_NAME_INPUT_MESSAGE);
//        String bookName = ConsoleReader.readString();
//        System.out.println(bookService.getBookByName(bookName));
//    }
    }
}
