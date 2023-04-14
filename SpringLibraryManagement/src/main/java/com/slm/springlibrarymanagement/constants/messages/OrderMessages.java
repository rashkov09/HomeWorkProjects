package com.slm.springlibrarymanagement.constants.messages;

public final class OrderMessages {

    public static final String ORDERS_OPTION_MESSAGE =
            """
                    Choose what to do with orders:
                    1. Print all orders
                    2. Print orders for client
                    3. Print orders issued on
                    4. Print orders issued before
                    5. Print orders issued after
                    6. Extend order due date
                    7. Add order
                        
                    0. Back
                    """;

    public static final String INSERT_DATE_MESSAGE = "Please, insert date in format dd/MM/yyyy:";
    public static final String BOOK_COUNT_MESSAGE = "How many books would you like to order?";
    public static final String CHOOSE_CLIENT_ID_MESSAGE = "Please, choose client ID from list above:";
    public static final String CHOOSE_BOOK_ID_MESSAGE = "Please, choose book ID from list above:";
    public static final String CHOOSE_ORDER_ID_MESSAGE = "Please, choose order ID from list above:";
    public static final String INSERT_DAY_COUNT = "Please, insert number of days:";
    public static final String INSERT_WEEK_COUNT = "Please, insert number of weeks:";
    public static final String INSERT_MONTH_COUNT = "Please, insert number of months:";
    public static final String ORDER_MODIFICATION_SUCCESS = "Order with ID %d modified successfully!";
    public static final String ORDER_MODIFICATION_FAILED = "Order with ID %d modified failed!";
    public static final String ORDER_ADDED_SUCCESSFULLY_MESSAGE = "Order of client %s for %d copies of book %s placed,successfully!";
    public static final String ORDER_ADDITION_FAILED_MESSAGE = "Order addition failed!";

}
