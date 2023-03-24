package com.lma.util;

import java.time.LocalDate;

public class LocalDateFormatter {
    public static LocalDate stringToLocalDate(String initial) {
        String[] dateValues = initial.split("/");
        return LocalDate.of(Integer.parseInt(dateValues[2]), Integer.parseInt(dateValues[1]), Integer.parseInt(dateValues[0]));
    }

    public static String localDateToString(LocalDate date) {
        return String.format("%s/%s/%s", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }
}