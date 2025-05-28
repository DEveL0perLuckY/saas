package com.backend.app.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequestDto {
    private String email;
    private String passwordHash;
    private String name;
    private String phoneNumber;
    private LocalDateTime dateCreated;

}
