package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.controller.UserController;
import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
public class UserControllerImpl  implements UserController {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllAuthor() {
        List<UserDto> userDtos= userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> getUserByUsername(String username) {
        UserDto userDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<UserDto> getUserByEmail(String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<Void> addUser(UserRequest userRequest) {
        Long id = userService.createUser(userRequest);

        URI location = UriComponentsBuilder.fromUriString("/user/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<UserDto> updateUser(UserRequest userRequest, int id, boolean returnOld) {
            UserDto userDto = userService.editUser(userRequest, id);
            if (returnOld) {
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.noContent().build();
            }
    }
}
