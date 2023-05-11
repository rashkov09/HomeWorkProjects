package com.slm.springlibrarymanagement.service;

import com.slm.springlibrarymanagement.model.dto.UserDto;

public interface UserService {

    Long register(UserDto userRequest);
}
