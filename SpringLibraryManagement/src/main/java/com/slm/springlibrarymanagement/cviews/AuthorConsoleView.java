package com.slm.springlibrarymanagement.cviews;


import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.stereotype.Component;

@Component
public class AuthorConsoleView implements ConsoleView {

    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 4;
    private static final String AUTHOR_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all authors
                    2. Search for a author by id
                    3. Search for a author by name
                    4. Add author
                                        
                    0. Back
                    """;
    private static final String AUTHOR_NAME_INPUT_MESSAGE = "Please, insert author name: ";
    private static final String AUTHOR_ID_INPUT_MESSAGE = "Please, insert author ID: ";
    private final AuthorService authorService;

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
                invoker.showItemMenu(invoker);
                return;
            case 1:
                printAll();
                break;
            case 2:
                findByAuthorId();
                break;
            case 3:
                findByAuthorName();
                break;
            case 4:
                addAuthor();
                break;
        }
        showItemMenu(invoker);
    }

    private void findByAuthorId() {
        System.out.println(AUTHOR_ID_INPUT_MESSAGE);
        String authorId = ConsoleReader.readString();
        try {
            System.out.println(authorService.findAuthorById(authorId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findByAuthorName() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        try {
            System.out.println(authorService.findAuthorByName(authorName).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addAuthor() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        try {
            System.out.println(authorService.insertAuthor(authorName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            addAuthor();
        }
    }

    private void printAll() {
        try {
            System.out.println(authorService.findAllAuthors());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
