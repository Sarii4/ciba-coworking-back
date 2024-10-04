package com.cibacoworking.cibacoworking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BodyErrorMessage {
    private int httpStatus;
    private String message;

}