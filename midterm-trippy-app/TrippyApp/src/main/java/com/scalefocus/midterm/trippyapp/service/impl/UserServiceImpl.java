package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.model.User;
import com.scalefocus.midterm.trippyapp.model.dto.UserDto;
import com.scalefocus.midterm.trippyapp.repository.UserRepository;
import com.scalefocus.midterm.trippyapp.service.ReviewService;
import com.scalefocus.midterm.trippyapp.service.UserService;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userCustomRepository;
    private final UserMapper userMapper;
    private final ReviewService reviewService;

    private final ObjectChecker<UserRequest> userObjectChecker;

    @Autowired
    public UserServiceImpl(UserRepository userCustomRepository, UserMapper userMapper, ReviewService reviewService, ObjectChecker<UserRequest> userObjectChecker) {
        this.userCustomRepository = userCustomRepository;
        this.userMapper = userMapper;
        this.reviewService = reviewService;
        this.userObjectChecker = userObjectChecker;
    }


    @Override
    public Long createUser(UserRequest userRequest) {
        userObjectChecker.checkForMissingFields(userRequest);
        User user = userMapper.mapFromRequest(userRequest);
        if (userExists(user)) {
            log.error(String.format("User with user %s or email %s already exists in database!", user.getUsername(), user.getEmail()));
            throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
        }
        try {
            Long id = userCustomRepository.add(user);
            log.info(String.format("User with username: %s and email: %s added successfully!", user.getUsername(), user.getEmail()));
            return id;
        } catch (SQLException e) {
            log.error(String.format("Unexpected error: %s", e.getMessage()));
            throw new RuntimeException("Unexpected error", e);
        }
    }

    @Override
    public UserDto editUser(UserRequest userRequest, Integer id) {
        userObjectChecker.checkForMissingFields(userRequest);
        User user = userMapper.mapFromRequest(userRequest);
        if (userExists(user)) {
            log.error(String.format("User with user %s or email %s already exists in database!", user.getUsername(), user.getEmail()));
            throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
        }
        try {
            User oldUser = userCustomRepository.edit(user, id.longValue());
            user.setId(id.longValue());
            log.info(String.format("User with id %d edited successfully!", id));
            return userMapper.mapToDto(oldUser);
        } catch (SQLException e) {
            log.error(String.format("Unexpected error: %s", e.getMessage()));
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
        user.setReviewList(reviewService.getReviewsByUsername(user.getUsername()));
        return userMapper.mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userCustomRepository.getAll();
        if (users.isEmpty()) {
            log.error("No data found in database!");
            throw new NoDataFoundException();
        }
        users.forEach(user -> user.setReviewList(reviewService.getReviewsByUsername(user.getUsername())));
        return users.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userCustomRepository.getByUsername(username);
        if (user == null) {
            log.info(String.format("User with username %s not found!", username));
            throw new UserNotFoundException("username " + username);
        }
        user.setReviewList(reviewService.getReviewsByUsername(user.getUsername()));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userCustomRepository.getByEmail(email);
        if (user == null) {
            log.info(String.format("User with email %s not found!", email));
            throw new UserNotFoundException("email " + email);
        }
        user.setReviewList(reviewService.getReviewsByUsername(user.getUsername()));
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