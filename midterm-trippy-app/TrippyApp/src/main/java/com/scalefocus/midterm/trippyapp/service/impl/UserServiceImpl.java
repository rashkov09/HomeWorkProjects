package com.scalefocus.midterm.trippyapp.service.impl;

import com.scalefocus.midterm.trippyapp.controller.request.UserRequest;
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

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Repository<User> repository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(Repository<User> repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public Long createUser(UserRequest userRequest) {
        User user = userMapper.mapFromRequest(userRequest);
        try {
            log.info("Author added successfully!");
            return repository.add(user);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")){
                throw new UserAlreadyExistsException(user.getUsername());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto editUser(UserRequest userRequest, Integer id) {
        User user =  repository.update(userMapper.mapFromRequest(userRequest),id.longValue());
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto getAuthorById(String id) {
        User user = repository.getById(Long.parseLong(id));
        if (user == null){
            log.error("Author not found!");
            throw new UserNotFoundException(Long.parseLong(id));
        }
      return   userMapper.mapToDto(user);
    }
}
