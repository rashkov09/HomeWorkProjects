package com.slm.springlibrarymanagement.cviews;


import com.slm.springlibrarymanagement.service.AuthorService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
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
                    2. Search for a author by id
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
        authorService.importAuthors();
       System.out.println(AUTHOR_OPTION_MESSAGE);
        System.out.print("Please, choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                authorService.backupToFile();
                invoker.showItemMenu(invoker);
                return;
            case 1:
                printAll();
                break;
            case 2:
               // TODO implement findByID
                break;
            case 3:
              // TODO implement findByBookName
                break;
            case 4:
                addAuthor();
                break;
        }
        showItemMenu(invoker);
    }

    private void addAuthor() {
        System.out.println(AUTHOR_NAME_INPUT_MESSAGE);
        String authorName = ConsoleReader.readString();
        // TODO validate input
        System.out.println(authorService.insertAuthor(authorName));
    }

    private void printAll() {
        System.out.println(authorService.findAllAuthors());
    }


}
