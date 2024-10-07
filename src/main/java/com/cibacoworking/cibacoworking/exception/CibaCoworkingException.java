package com.cibacoworking.cibacoworking.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CibaCoworkingException extends Exception {
    
    private HttpStatus httpStatus;

    public CibaCoworkingException(String message) {
        super(message);
    }
    
    public CibaCoworkingException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }


}