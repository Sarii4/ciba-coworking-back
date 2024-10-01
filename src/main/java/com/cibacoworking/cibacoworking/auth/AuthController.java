package com.cibacoworking.cibacoworking.auth;

import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.models.dtos.RegisterRequest; 
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthService authService; 

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = new User(registerRequest); 
        authService.registerUser(user); 
        return ResponseEntity.ok("Usuario registrado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword()); 
        if (user != null) {
            String token = authService.generateToken(user); 
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }
}
