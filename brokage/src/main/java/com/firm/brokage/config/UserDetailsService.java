package com.firm.brokage.config;

import com.firm.brokage.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${security.oauth2.username:admin}")
    String username;
    @Value("${security.oauth2.password:pass123}")
    String password;

    public UserDetailsService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        if (username.equals(user)) {
            return new User(username, passwordEncoder.encode(password), new ArrayList<>());
        }
        throw new UsernameNotFoundException(username);
    }

    public UserDetails loadUserByRequest(AuthenticationRequest authenticationRequest) throws UsernameNotFoundException {
        if (username.equals(authenticationRequest.getUsername()) &&
                passwordEncoder.encode(password).equalsIgnoreCase(authenticationRequest.getPassword())) {
            return new User(username, passwordEncoder.encode(password), new ArrayList<>());
        }
        throw new UsernameNotFoundException(username);
    }
}