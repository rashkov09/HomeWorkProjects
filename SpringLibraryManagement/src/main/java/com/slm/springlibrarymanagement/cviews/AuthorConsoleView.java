package com.slm.springlibrarymanagement.cviews;


import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.slm.springlibrarymanagement.constants.messages.AuthorMessages.*;
import static com.slm.springlibrarymanagement.constants.messages.GeneralMessages.CHOOSE_AN_OPTION_MESSAGE;

@Component
public class AuthorConsoleView implements ConsoleView {

    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 4;
    private final AuthorService authorService;
    private final ConsoleReader consoleReader;
    private final ConsoleRangeReader consoleRangeReader;

    @Autowired
    public AuthorConsoleView(AuthorService authorService, ConsoleReader consoleReader, ConsoleRangeReader consoleRangeReader) {
        this.authorService = authorService;
        this.consoleReader = consoleReader;
        this.consoleRangeReader = consoleRangeReader;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(AUTHOR_OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = consoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
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
        String authorId = consoleReader.readString();
        try {
            System.out.println(authorService.findAuthorById(authorId));
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    private void findByAuthorName() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = consoleReader.readString();
        try {
            System.out.println(authorService.findAuthorByName(authorName).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addAuthor() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = consoleReader.readString();
        try {
            System.out.println(authorService.insertAuthor(authorName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
