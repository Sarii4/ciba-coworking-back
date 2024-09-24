package com.cibacoworking.cibacoworking.models;

import com.cibacoworking.cibacoworking.models.entities.User;

public class Login {
    
    private User user;
    private String token;
    private boolean authenticated;

    public Login() {}

    public Login(User user, String token, boolean authenticated) {
        this.user = user;
        this.token = token;
        this.authenticated = authenticated;
    }

   
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
