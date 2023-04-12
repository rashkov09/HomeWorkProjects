package com.slm.springlibrarymanagement.util;

import java.time.format.DateTimeFormatter;

public class CustomDateFormatter {

    public CustomDateFormatter() {
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

}
