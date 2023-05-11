package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.UserController;
import com.slm.springlibrarymanagement.controller.request.UserRequest;
import com.slm.springlibrarymanagement.mappers.UserMapper;
import com.slm.springlibrarymanagement.model.dto.UserDto;
import com.slm.springlibrarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserControllerImpl(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<Void> registerUser(UserRequest userRequest) {
        UserDto userDto = userMapper.mapRequestToDto(userRequest);
        Long id = userService.register(userDto);

        URI location = UriComponentsBuilder.fromUriString("/users/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
