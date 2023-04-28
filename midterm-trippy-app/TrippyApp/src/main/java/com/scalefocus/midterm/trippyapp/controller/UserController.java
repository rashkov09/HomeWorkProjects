package com.scalefocus.midterm.trippyapp.controller;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/users")
public interface UserController {

    @GetMapping()
    ResponseEntity<List<UserDto>> getAllUsers();

    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@PathVariable
                                        Long id);

    @GetMapping(params = "username")
    ResponseEntity<UserDto> getUserByUsername(@RequestParam(value = "username") String username);

    @GetMapping(params = "email")
    ResponseEntity<UserDto> getUserByEmail(@RequestParam(value = "email") String email);

    @PostMapping()
    ResponseEntity<Void> addUser(@RequestBody @Valid UserRequest userRequest);

    @PutMapping("/{id}")
    ResponseEntity<UserDto> updateUser(
            @RequestBody @Valid UserRequest userRequest, @PathVariable int id,
            @RequestParam(required = false) boolean returnOld);

}
