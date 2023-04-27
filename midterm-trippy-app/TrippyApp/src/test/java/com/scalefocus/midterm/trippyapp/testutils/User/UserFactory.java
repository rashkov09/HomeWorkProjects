package com.scalefocus.midterm.trippyapp.testutils.User;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;

import static com.scalefocus.midterm.trippyapp.testutils.User.UserConstants.*;

public final class UserFactory {
    private UserFactory() {
        throw new IllegalStateException();
    }

    public static User getDefaultUser(){
        return new User(USER_ID,USER_USERNAME,USER_EMAIL,USER_FIRSTNAME,USER_LASTNAME,USER_CITY);
    }

    public static UserDto getDefaultUserDto(){
        return new UserDto(USER_ID,USER_USERNAME,USER_EMAIL,USER_FIRSTNAME,USER_LASTNAME,USER_CITY,USER_JOIN_DATE);
    }

    public static UserRequest getDefaultUserRequest(){
        return new UserRequest(USER_USERNAME,USER_EMAIL,USER_FIRSTNAME,USER_LASTNAME,USER_CITY);
    }
}
