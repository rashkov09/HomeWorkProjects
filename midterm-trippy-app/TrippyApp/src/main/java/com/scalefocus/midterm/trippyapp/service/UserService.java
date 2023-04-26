package com.scalefocus.midterm.trippyapp.service;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;

public interface UserService {
    Long createUser(UserRequest userRequest);

    UserDto editUser(UserRequest itemRequest, Integer id);

    UserDto getAuthorById(String id);
}
