package com.slm.springlibrarymanagement.cviews;

import com.slm.springlibrarymanagement.constants.IncreasePeriod;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.order.OrderNotFoundException;
import com.slm.springlibrarymanagement.service.OrderService;
import com.slm.springlibrarymanagement.util.ConsoleRangeReader;
import com.slm.springlibrarymanagement.util.ConsoleReader;
import org.springframework.stereotype.Component;

import static com.slm.springlibrarymanagement.constants.messages.GeneralMessages.CHOOSE_AN_OPTION_MESSAGE;
import static com.slm.springlibrarymanagement.constants.messages.OrderMessages.*;


@Component
public class ExtendDueDateConsoleView implements ConsoleView {
    private static final int MIN_MENU_OPTION = 0;
    private static final int MAX_MENU_OPTION = 3;
    private static final String EXTEND_DUE_DATE_MESSAGE = """
            Please, select extend period:
                1. DAYS
                2. WEEKS
                3. MONTHS
                
                0. Back
            """;
    private final OrderService orderService;


    public ExtendDueDateConsoleView(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void showItemMenu(ConsoleView invoker) {
        System.out.println(EXTEND_DUE_DATE_MESSAGE);
        System.out.print(CHOOSE_AN_OPTION_MESSAGE);
        int choice = ConsoleRangeReader.readInt(MIN_MENU_OPTION, MAX_MENU_OPTION);
        switch (choice) {
            case 0 -> {
                invoker.showItemMenu(this);
                return;
            }
            case 1 -> {
                Long id = getId();
                extendDueDate(id, IncreasePeriod.DAY);
            }
            case 2 -> {
                Long id = getId();
                extendDueDate(id, IncreasePeriod.WEEK);
            }
            case 3 -> {
                Long id = getId();
                extendDueDate(id, IncreasePeriod.MONTH);
            }
        }
        showItemMenu(invoker);
    }

    private Long getId() {
        try {
            System.out.println(orderService.findAllOrders());
        } catch (NoEntriesFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(CHOOSE_ORDER_ID_MESSAGE);
        Long id = ConsoleReader.readLong();
        return id;
    }

    private void extendDueDate(Long id, IncreasePeriod period) {
        switch (period) {
            case DAY -> System.out.println(INSERT_DAY_COUNT);
            case WEEK -> System.out.println(INSERT_WEEK_COUNT);
            case MONTH -> System.out.println(INSERT_MONTH_COUNT);
        }
        int count = ConsoleReader.readInt();
        try {
            System.out.println(orderService.extendOrderDueDate(id, count, period));
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
