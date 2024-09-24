package com.cibacoworking.cibacoworking.models;

public class Logout {

    private String message;
    private boolean success;

    public Logout() {}

    public Logout(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
