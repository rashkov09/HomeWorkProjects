package com.slm.springlibrarymanagement.mappers;

import com.slm.springlibrarymanagement.controller.request.UserRequest;
import com.slm.springlibrarymanagement.model.dto.UserDto;
import com.slm.springlibrarymanagement.model.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


    public UserDto mapRequestToDto(UserRequest userRequest){
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        authorities.add(new SimpleGrantedAuthority("CUSTOMER"));

        return new UserDto(userRequest.getUsername(), userRequest.getPassword(),authorities);
    }

    public User mapDtoToUser(UserDto userDto){
        String encodedPassword = encoder.encode(userDto.getPassword());
        return new User(userDto.getUsername(),encodedPassword, userDto.getAuthorities().toString());
    }
}
