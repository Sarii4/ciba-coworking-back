package com.cibacoworking.cibacoworking.models.dtos.responses;

import com.cibacoworking.cibacoworking.models.dtos.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class AuthResponseDTO {
    private String token;
    private UserDTO userDTO;
}
