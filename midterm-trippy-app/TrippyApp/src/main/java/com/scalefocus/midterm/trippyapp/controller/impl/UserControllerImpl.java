package com.scalefocus.midterm.trippyapp.controller.impl;

import com.scalefocus.midterm.trippyapp.controller.UserController;
import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.BusinessDto;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.service.BusinessService;
import com.scalefocus.midterm.trippyapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final BusinessService businessService;

    @Autowired
    public UserControllerImpl(UserService userService, BusinessService businessService) {
        this.userService = userService;
        this.businessService = businessService;
    }


    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<List<BusinessDto>> getBusinessByUserCity(Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(businessService.getBusinessesByCity(userDto.getCity()));
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

        URI location = UriComponentsBuilder.fromUriString("/users/{id}")
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
