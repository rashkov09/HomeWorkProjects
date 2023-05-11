package com.slm.springlibrarymanagement.controller;

import com.slm.springlibrarymanagement.controller.request.JwtRequest;
import com.slm.springlibrarymanagement.jwt.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/auth")
public interface AuthenticationController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<JwtToken> login(@RequestBody JwtRequest jwtRequest);
}
