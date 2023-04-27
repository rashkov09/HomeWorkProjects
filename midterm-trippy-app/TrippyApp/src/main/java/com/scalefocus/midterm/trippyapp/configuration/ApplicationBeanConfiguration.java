package com.scalefocus.midterm.trippyapp.configuration;

import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
}
