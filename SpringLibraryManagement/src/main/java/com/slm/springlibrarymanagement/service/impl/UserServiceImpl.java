package com.slm.springlibrarymanagement.service.impl;

import com.slm.springlibrarymanagement.mappers.UserMapper;
import com.slm.springlibrarymanagement.model.dto.UserDto;
import com.slm.springlibrarymanagement.model.entities.User;
import com.slm.springlibrarymanagement.repository.UserRepository;
import com.slm.springlibrarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Long register(UserDto userDto) {
        User user = userMapper.mapDtoToUser(userDto);
        return userRepository.save(user).getId();
    }
}
