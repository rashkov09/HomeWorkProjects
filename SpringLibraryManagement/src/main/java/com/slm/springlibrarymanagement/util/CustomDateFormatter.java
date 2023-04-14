package com.slm.springlibrarymanagement.util;

import java.time.format.DateTimeFormatter;

public class CustomDateFormatter {
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public CustomDateFormatter() {
    }

    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

}
