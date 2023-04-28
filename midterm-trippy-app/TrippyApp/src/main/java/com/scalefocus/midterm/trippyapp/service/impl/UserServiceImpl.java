package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.repository.CustomRepository;
import com.scalefocus.midterm.trippyapp.service.UserService;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CustomRepository<User> userCustomRepository;
    private final UserMapper userMapper;

    private final ObjectChecker<UserRequest> userObjectChecker;

    @Autowired
    public UserServiceImpl(CustomRepository<User> userCustomRepository, UserMapper userMapper, ObjectChecker<UserRequest> userObjectChecker) {
        this.userCustomRepository = userCustomRepository;
        this.userMapper = userMapper;
        this.userObjectChecker = userObjectChecker;
    }


    @Override
    public Long createUser(UserRequest userRequest) {
        userObjectChecker.checkForMissingFields(userRequest);
        User user = userMapper.mapFromRequest(userRequest);
        if (userExists(user)) {
            throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
        }
        try {
            Long id = userCustomRepository.add(user);
            log.info(String.format("User with username: %s and email: %s added successfully!", user.getUsername(), user.getEmail()));
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public UserDto editUser(UserRequest userRequest, Integer id) {
        userObjectChecker.checkForMissingFields(userRequest);
        User user = userMapper.mapFromRequest(userRequest);
        if (userExists(user)) {
            throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
        }
        try {
            User oldUser = userCustomRepository.update(user, id.longValue());
            user.setId(id.longValue());
            log.info(String.format("User with id %d edited successfully!", id));
            return userMapper.mapToDto(oldUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userCustomRepository.getById(id);
        if (user == null) {
            log.info(String.format("User with id %d not found!", id));
            throw new UserNotFoundException("id " + id);
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = userCustomRepository.getAll().stream().map(userMapper::mapToDto).toList();
        if (userDtos.isEmpty()) {
            log.error("No data found in database!");
            throw new NoDataFoundException();
        }
        return userDtos;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userCustomRepository.getByUsername(username);
        if (user == null) {
            log.info(String.format("User with username %s not found!", username));
            throw new UserNotFoundException("username " + username);
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userCustomRepository.getByEmail(email);
        if (user == null) {
            log.info(String.format("User with email %s not found!", email));
            throw new UserNotFoundException("email " + email);
        }
        return userMapper.mapToDto(user);
    }

    @Override
    public Boolean userExists(User user) {
        try {
            getUserByEmail(user.getEmail());
        } catch (UserNotFoundException e) {
            try {
                getUserByUsername(user.getUsername());
            } catch (UserNotFoundException d) {
                return false;
            }
        }
        return true;
    }
}