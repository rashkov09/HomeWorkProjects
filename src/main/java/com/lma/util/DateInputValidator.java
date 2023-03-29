package com.lma.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateInputValidator {
    private final static Pattern pattern;
    private final static String VALID_DATE_FORMAT = "^([0-2][0-9]|3[0-1])/([0-1][0-2]|0[0-9])/([0-9]{4})$";

    static {
        pattern = Pattern.compile(VALID_DATE_FORMAT);
    }

    public static Boolean validate(String date){
        Matcher matcher = pattern.matcher(date);
        return !date.isEmpty() && matcher.matches();
    }

}
