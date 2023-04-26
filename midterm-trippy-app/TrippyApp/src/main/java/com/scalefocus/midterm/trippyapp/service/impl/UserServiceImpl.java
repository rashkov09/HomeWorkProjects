package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.repository.Repository;
import com.scalefocus.midterm.trippyapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Repository<User> userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(Repository<User> userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Long createUser(UserRequest userRequest) {
        User user = userMapper.mapFromRequest(userRequest);
        try {
            log.info("Author added successfully!");
            return userRepository.add(user);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
            }
            if (e.getSQLState().equals("23502")) {
                throw new NullPointerException(e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto editUser(UserRequest userRequest, Integer id) {
        User user = userMapper.mapFromRequest(userRequest);
        try {
            userRepository.update(user, id.longValue());
            return userMapper.mapToDto(user);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
            }
            if (e.getSQLState().equals("23502")) {
                throw new NullPointerException(e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getById(id);
        if (user == null) {
            log.error("User not found!");
            throw new UserNotFoundException("id " + id);
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = userRepository.getAll().stream().map(userMapper::mapToDto).toList();
        if (userDtos.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userDtos;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            log.error("User not found!");
            throw new UserNotFoundException("username " + username);
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            log.error("User not found!");
            throw new UserNotFoundException("email " + email);
        }
        return userMapper.mapToDto(user);
    }
}
