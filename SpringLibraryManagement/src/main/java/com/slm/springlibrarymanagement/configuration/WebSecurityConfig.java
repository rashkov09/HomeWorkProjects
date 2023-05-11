package com.slm.springlibrarymanagement.configuration;

import com.slm.springlibrarymanagement.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final JwtRequestFilter requestFilter;

    @Autowired
    public WebSecurityConfig(JwtRequestFilter requestFilter) {
        this.requestFilter = requestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable()
               .authorizeHttpRequests()
               .requestMatchers("/api/auth/login")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}