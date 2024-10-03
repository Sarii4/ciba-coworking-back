package com.cibacoworking.cibacoworking.config.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.config.ConstantsSecurity;


@RestController
public class AuthController {

    private final AuthService authService;
   
    public AuthController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(ConstantsSecurity.LOGIN_URL)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.login(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (CibaCoworkingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //falta logout
}


