package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.service.OrderService;
import org.springframework.stereotype.Component;


@Component
public class ExtendDueDateConsoleView implements ConsoleView {
    private final  OrderService orderService;
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 3;
    private static final String EXTEND_DUE_DATE_MESSAGE = """
            Please, select extend period:
                1. DAYS
                2. WEEKS
                3. MONTHS
                
                0. Back
            """;


    public ExtendDueDateConsoleView( OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
       System.out.println(EXTEND_DUE_DATE_MESSAGE);
//        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
//        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
//        switch (choice) {
//            case 0:
//                orderConsoleView.showItemMenu();
//                return;
//            case 1:
//                extendDueDate(IncreasePeriod.DAY);
//                break;
//            case 2:
//                extendDueDate(IncreasePeriod.WEEK);
//                break;
//            case 3:
//                extendDueDate(IncreasePeriod.MONTH);
//                break;
//        }
//        showItemMenu();
    }

//    private void extendDueDate(IncreasePeriod period) {
//        System.out.println(CLIENT_NAME_INPUT_MESSAGE);
//        String clientName = ConsoleReader.readString();
//        System.out.println(BOOK_NAME_INPUT_MESSAGE);
//        String bookName = ConsoleReader.readString();
//        int count = ConsoleReader.readInt();
//        System.out.println(orderService.extendOrderDueDate(clientName, bookName,count,period));
//    }

}
