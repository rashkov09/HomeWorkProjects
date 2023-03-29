package com.lma.cviews;

import com.lma.constants.enums.IncreasePeriod;
import com.lma.service.OrderService;
import com.lma.service.impl.OrderServiceImpl;
import com.lma.util.ConsoleRangeReader;
import com.lma.util.ConsoleReader;

import static com.lma.constants.CustomMessages.*;

public class ExtendDueDateConsoleView implements ConsoleView {
    private final static OrderConsoleView orderConsoleView = new OrderConsoleView();
    private final static OrderService orderService = new OrderServiceImpl();
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 3;
    private static final String EXTEND_DUE_DATE_MESSAGE = """
            Please, select extend period:
                1. DAYS
                2. WEEKS
                3. MONTHS
                
                0. Back
            """;

    @Override
    public void showItemMenu() {
        System.out.println(EXTEND_DUE_DATE_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0:
                orderConsoleView.showItemMenu();
                return;
            case 1:
                extendDueDate(IncreasePeriod.DAY);
                break;
            case 2:
                extendDueDate(IncreasePeriod.WEEK);
                break;
            case 3:
                extendDueDate(IncreasePeriod.MONTH);
                break;
        }
        showItemMenu();
    }

    private void extendDueDate(IncreasePeriod period) {
        System.out.println(CLIENT_NAME_INPUT_MESSAGE);
        String clientName = ConsoleReader.readString();
        System.out.println(BOOK_NAME_INPUT_MESSAGE);
        String bookName = ConsoleReader.readString();
        int count = ConsoleReader.readInt();
        System.out.println(orderService.extendOrderDueDate(clientName, bookName,count,period));
    }

}
