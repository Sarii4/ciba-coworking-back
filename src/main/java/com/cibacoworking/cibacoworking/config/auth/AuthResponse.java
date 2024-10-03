package com.cibacoworking.cibacoworking.config.auth;

import com.cibacoworking.cibacoworking.models.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class AuthResponse {
    private String token;
    private UserDTO userDTO;
}
