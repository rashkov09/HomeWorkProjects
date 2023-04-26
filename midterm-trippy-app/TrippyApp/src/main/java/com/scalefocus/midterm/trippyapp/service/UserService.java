package com.scalefocus.midterm.trippyapp.service;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;

import java.util.List;

public interface UserService {
    Long createUser(UserRequest userRequest);

    UserDto editUser(UserRequest itemRequest, Integer id);

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto getUserByUsername(String username);

    UserDto getUserByEmail(String email);
}
