package com.slm.springlibrarymanagement.configuration;

import com.slm.springlibrarymanagement.util.InputValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public InputValidator nameValidator() {
        return new InputValidator();
    }

}
