package com.scalefocus.midterm.trippyapp.testutils.User;

import com.scalefocus.midterm.trippyapp.model.Review;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class UserConstants {
    public UserConstants() {
        throw new IllegalArgumentException();
    }

    public static final Long USER_ID = 1L;
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "testemail@email.com";
    public static final String USER_FIRSTNAME = "Test";
    public static final String USER_LASTNAME= "Testov";
    public static final String USER_CITY= "Stalingrad";
    public static final LocalDate USER_JOIN_DATE= LocalDate.of(2023,4,27);
    public static final List<Review> USER_REVIEWS= new ArrayList<>();


}
