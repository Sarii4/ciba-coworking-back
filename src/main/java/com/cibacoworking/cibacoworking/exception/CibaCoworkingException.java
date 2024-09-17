package com.cibacoworking.cibacoworking.exception;

import org.springframework.http.HttpStatus;

public class CibaCoworkingException extends Exception {
    private HttpStatus httpStatus;

    public CibaCoworkingException(String message) {
        super(message);
    }
    
    public CibaCoworkingException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}