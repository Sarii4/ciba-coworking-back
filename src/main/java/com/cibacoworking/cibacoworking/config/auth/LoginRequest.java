package com.cibacoworking.cibacoworking.config.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    
    private String email;
    private String password;
}
