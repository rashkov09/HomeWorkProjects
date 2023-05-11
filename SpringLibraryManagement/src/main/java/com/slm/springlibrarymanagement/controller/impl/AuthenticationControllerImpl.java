package com.slm.springlibrarymanagement.controller.impl;

import com.slm.springlibrarymanagement.controller.AuthenticationController;
import com.slm.springlibrarymanagement.controller.request.JwtRequest;
import com.slm.springlibrarymanagement.jwt.JwtToken;
import com.slm.springlibrarymanagement.jwt.JwtTokenUtil;
import com.slm.springlibrarymanagement.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationControllerImpl implements AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public AuthenticationControllerImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    public ResponseEntity<JwtToken> login(JwtRequest jwtRequest) {
        authenticateUser(jwtRequest.getUsername(), jwtRequest.getPassword());
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String responseJwtToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtToken(responseJwtToken));
    }


    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
