package com.cibacoworking.cibacoworking.models.dtos.auth;

public class LoginResponse {
    private String token;

    // Constructor
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter del token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
