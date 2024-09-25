package com.cibacoworking.cibacoworking.models.dtos.auth;

import com.cibacoworking.cibacoworking.models.entities.User;

public class Login {
    private User user;
    private String token;
    private boolean success;

    public Login(User user, String token, boolean success) {
        this.user = user;
        this.token = token;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
