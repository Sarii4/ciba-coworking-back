package com.cibacoworking.cibacoworking.models;

import com.cibacoworking.cibacoworking.models.entities.User;

public class Login {
    
    private final User user;
    private final String token;
    private final boolean authenticated;

    public Login(User user, String token, boolean authenticated) {
        this.user = user;
        this.token = token;
        this.authenticated = authenticated;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
