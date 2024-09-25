package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.config.security.JwtUtil;
import com.cibacoworking.cibacoworking.models.dtos.auth.LoginResponse; 
import com.cibacoworking.cibacoworking.models.dtos.auth.LoginRequest;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid; 

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Intentar autenticar al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            // Si la autenticación falla, devolver un estado 401 (No autorizado)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }

        // Generar un token JWT para el usuario autenticado
        String token = jwtUtil.generateToken(loginRequest.getEmail());
        
        // Obtener el usuario autenticado
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Crear una respuesta de inicio de sesión con el token
        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
