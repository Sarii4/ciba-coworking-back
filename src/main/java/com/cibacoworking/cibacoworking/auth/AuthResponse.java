package com.cibacoworking.cibacoworking.auth;

public class AuthResponse {
    private String token;

    // Constructor
    public AuthResponse(String token) {
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
