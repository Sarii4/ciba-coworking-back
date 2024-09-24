package com.cibacoworking.cibacoworking.models;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
