package com.slm.springlibrarymanagement.cviews;


import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import org.springframework.stereotype.Component;

@Component
public class AuthorConsoleView implements ConsoleView {

    private final AuthorService authorService;
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 4;
    private static final String AUTHOR_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all authors
                    2. Search for a author by name
                    3. Search for a author by book name
                    4. Add author
                                        
                    0. Back
                    """;

    private static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert author name: ";

    public AuthorConsoleView(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
       System.out.println(AUTHOR_OPTION_MESSAGE);
        System.out.print("Please, choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                invoker.showItemMenu(this);
                return;
            case 1:
                break;
//            case 2:
//                printAuthorByName();
//                break;
//            case 3:
//                printAuthorByBook();
//                break;
//            case 4:
//                addAuthor();
//                break;
        }
        showItemMenu(this);
    }

//    private void printAuthorByBook() {
//        System.out.println(BOOK_NAME_INPUT_MESSAGE);
//    String authorName = ConsoleReader.readString();
//        System.out.println(authorService.findAuthorByBookName(authorName));
//}
//
//    private void printAuthorByName() {
//        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
//        String authorName = ConsoleReader.readString();
//        System.out.println(authorService.findAuthorByName(authorName));
//    }
//
//
//    public void printAllAuthors() {
//        System.out.println(authorService.getAllAuthors());
//    }
//
//
//    public void addAuthor() {
//        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
//        String authorName = ConsoleReader.readString();
//        System.out.println(authorService.addAuthor(authorName));
//    }
}
