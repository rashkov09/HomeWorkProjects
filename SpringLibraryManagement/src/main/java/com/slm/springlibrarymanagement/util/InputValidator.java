package com.slm.springlibrarymanagement.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public boolean isNotValidFullName(String authorName) {
        String regex = "^(([a-z-A-Z]+.+)+|[a-zA-Z]+) [A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorName);
        return !matcher.matches();
    }

    public boolean isNotValidFirstLastName(String authorName) {
        String regex = "^[a-z-A-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorName);
        return !matcher.matches();
    }

    public boolean isNotValidPhoneNumber(String authorName) {
        String regex = "^(\\+[1-9]{1,3})([0-9]{5,9})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorName);
        return !matcher.matches();
    }
     public boolean isNotValidDate(String date) {
        String regex = "^(3[0-1]|[0-2][0-2])/(1[0-2]|0[1-9])/([1-9][0-9]{3})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return !matcher.matches();
    }


}
