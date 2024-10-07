package com.cibacoworking.cibacoworking.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.requests.LoginRequestDTO;
import com.cibacoworking.cibacoworking.models.dtos.responses.AuthResponseDTO;
import com.cibacoworking.cibacoworking.services.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;


    @PostMapping(EndpointsConstants.LOGIN_URL)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            AuthResponseDTO authResponse = authService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (CibaCoworkingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}


