package com.slm.springlibrarymanagement.exceptions.order;

public class OrderNotFoundException extends Exception{

    private final String ORDER_NOT_FOUND_MESSAGE = "Order with ID %d not found!";
    @Override
    public String getMessage() {
        return   ORDER_NOT_FOUND_MESSAGE;
    }
}
