package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.controller.UserController;
import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class UserControllerImpl  implements UserController {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDto> getUserById(String id) {
        UserDto userDto = userService.getAuthorById(id);
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
    public ResponseEntity<UserDto> updateItem(UserRequest itemRequest, int id, boolean returnOld) {
            UserDto itemDto = userService.editUser(itemRequest, id);
            if (returnOld) {
                return ResponseEntity.ok(itemDto);
            } else {
                return ResponseEntity.noContent().build();
            }
    }
}
