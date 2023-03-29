package com.lma.cviews;

import com.lma.util.ConsoleRangeReader;

import static com.lma.constants.CustomMessages.CHOOSE_AN_OPTION_MESSAGE;
import static java.lang.System.exit;

public class MainMenuView implements ConsoleView {
    private final static ConsoleView authorConsoleView = new AuthorConsoleView();
    private final static ConsoleView bookConsoleView = new BookConsoleView();
    private final static ConsoleView clientConsoleView = new ClientConsoleView();
    private final static ConsoleView orderConsoleView = new OrderConsoleView();
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 4;
    private static final String OPTION_MESSAGE =
            """
                    Choose what to manage:
                    1. Books
                    2. Authors
                    3. Clients
                    4. Orders
                                        
                    0. Exit
                    """;

    @Override
    public void showItemMenu() {
        System.out.println(OPTION_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);

        switch (choice) {
            case 0:
                exit(1);
            case 1:
                bookConsoleView.showItemMenu();
                break;
            case 2:
                authorConsoleView.showItemMenu();
                break;
            case 3:
                clientConsoleView.showItemMenu();
                break;
            case 4:
                orderConsoleView.showItemMenu();
                break;
        }
    }
}
