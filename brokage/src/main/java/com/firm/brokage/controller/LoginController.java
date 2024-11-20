package com.firm.brokage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firm.brokage.config.TokenManager;
import com.firm.brokage.model.AuthenticationRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/"})
public class LoginController {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    AuthenticationProvider authenticationProvider;
    @Autowired
    private TokenManager tokenManager;


    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String token = tokenManager.generateToken(request.getUsername());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        } catch (AuthenticationException e) {
            logger.debug("Could not generate token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
