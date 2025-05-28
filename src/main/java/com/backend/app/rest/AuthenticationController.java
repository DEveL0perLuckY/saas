package com.backend.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.dto.LoginReq;
import com.backend.app.dto.RegisterRequestDto;
import com.backend.app.service.AuthenticationResponse;
import com.backend.app.service.AuthenticationService;
import com.backend.app.util.ApiResponse;

@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(
            @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @RequestBody LoginReq request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
