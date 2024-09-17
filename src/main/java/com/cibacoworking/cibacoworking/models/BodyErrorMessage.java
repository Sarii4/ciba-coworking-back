package com.cibacoworking.cibacoworking.models;

public class BodyErrorMessage {
    private int httpStatus;
    private String message;

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    

    public int getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }


}