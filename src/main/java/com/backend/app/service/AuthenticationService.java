package com.backend.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.app.domain.User;
import com.backend.app.dto.LoginReq;
import com.backend.app.dto.RegisterRequestDto;
import com.backend.app.repos.UserRepository;
import com.backend.app.util.ApiResponse;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;



    @Autowired
    AuthenticationManager authenticationManager;

    public ApiResponse<AuthenticationResponse> register(RegisterRequestDto request) {
        try {

            if (appUserRepository.findByEmail(request.getEmail()).isPresent()) {
                return new ApiResponse<>(null, "Email already exists", HttpStatus.BAD_REQUEST);
            }

            User user = createUser(request);
            String jwt = jwtService.generateToken(user);
            user.setToken(jwt);
            appUserRepository.save(user);

            AuthenticationResponse authResponse = new AuthenticationResponse(jwt, user.getUserId());
            return new ApiResponse<>(authResponse, "Registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ApiResponse<>(null, "Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private User createUser(RegisterRequestDto request) {
        User user = new User();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPasswordHash()));
       user.setCreatedAt(request.getDateCreated());
        return user;
    }



    public ApiResponse<AuthenticationResponse> authenticate(LoginReq request) {
        try {

            authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = appUserRepository.findByEmail(request.getEmail()).orElseThrow();
            String jwt = jwtService.generateToken(user);

            user.setToken(jwt);
            appUserRepository.save(user);

            AuthenticationResponse authResponse = new AuthenticationResponse(jwt, user.getUserId());
            return new ApiResponse<>(authResponse, "Authentication successful", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ApiResponse<>(null, "Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

    }

}
