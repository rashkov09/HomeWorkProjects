package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;

@Component
public class MainMenuView implements ConsoleView {
    private  final ConsoleView authorConsoleView;
    private final  ConsoleView bookConsoleView;
    private final  ConsoleView clientConsoleView;
    private final  ConsoleView orderConsoleView;
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

    public MainMenuView(ConsoleView authorConsoleView, ConsoleView bookConsoleView,
                        ConsoleView clientConsoleView, ConsoleView orderConsoleView) {
        this.authorConsoleView = authorConsoleView;
        this.bookConsoleView = bookConsoleView;
        this.clientConsoleView = clientConsoleView;
        this.orderConsoleView = orderConsoleView;

    }

    @Override
    public  void showItemMenu(ConsoleView invoker) {
       System.out.println(OPTION_MESSAGE);
       System.out.print("Please choose an option: ");
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                exit(1);
            case 1:
                bookConsoleView.showItemMenu(this);
                break;
            case 2:
                authorConsoleView.showItemMenu(this);
                break;
            case 3:
                clientConsoleView.showItemMenu(this);
                break;
            case 4:
                orderConsoleView.showItemMenu(this);
                break;
        }
    }
}
