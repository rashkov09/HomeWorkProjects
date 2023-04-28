package com.scalefocus.midterm.trippyapp.configuration;

import com.scalefocus.midterm.trippyapp.mapper.BusinessMapper;
import com.scalefocus.midterm.trippyapp.mapper.UserMapper;
import com.scalefocus.midterm.trippyapp.util.ObjectChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }

    @Bean
    public BusinessMapper businessMapper() {
        return new BusinessMapper();
    }

    @Bean
    public ObjectChecker objectChecker() {
        return new ObjectChecker();
    }

}
