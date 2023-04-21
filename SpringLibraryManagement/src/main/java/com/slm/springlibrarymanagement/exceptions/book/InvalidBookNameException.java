package com.slm.springlibrarymanagement.exceptions.book;

public class InvalidBookNameException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Book name is empty!";
    }
}
