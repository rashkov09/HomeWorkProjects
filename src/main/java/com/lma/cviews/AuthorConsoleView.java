package com.lma.cviews;

import com.lma.service.AuthorService;
import com.lma.service.impl.AuthorServiceImpl;
import com.lma.util.ConsoleRangeReader;
import com.lma.util.ConsoleReader;

import static com.lma.constatns.CustomMessages.BOOK_NAME_INPUT_MESSAGE;
import static com.lma.constatns.CustomMessages.CHOOSE_AN_OPTION_MESSAGE;

public class AuthorConsoleView implements ConsoleView{
    private  final static ConsoleView mainConsoleView = new MainMenuView();
    private final static AuthorService authorService = new AuthorServiceImpl();
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
    private static final String AUTHOR_ADDED_SUCCESSFULLY_MESSAGE = "%s added successfully!\n";


    @Override
    public void showItemMenu() {
        System.out.println(AUTHOR_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                mainConsoleView.showItemMenu();
                break;
            case 1:
                printAllAuthors();
                break;
            case 2:
                printAuthorByName();
                break;
            case 3:
                printAuthorByBook();
                break;
            case 4:
              addAuthor();
                break;
        }
        showItemMenu();
    }

    private void printAuthorByBook() {
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        System.out.println(authorService.findAuthorByBookName(authorName));
    }

    private void printAuthorByName() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        System.out.println(authorService.findAuthorByName(authorName));
    }



    public void printAllAuthors() {
        System.out.println(authorService.getAllAuthors());
    }


    public void addAuthor() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
       if (authorService.addAuthor(authorName)){
           System.out.printf(authorName,AUTHOR_ADDED_SUCCESSFULLY_MESSAGE);
       };
    }
}
