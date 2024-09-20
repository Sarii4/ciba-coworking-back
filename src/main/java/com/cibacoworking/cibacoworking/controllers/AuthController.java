package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        //Chicas aquí  utilizamos 'authentication' para obtener detalles del usuario.
        String userName = authentication.getName(); // Ejemplo de uso

        // Aquí podemos añadir lógica para generar un token JWT si es necesario.

        return ResponseEntity.ok("Inici de sessió correcte per a l'usuari: " + userName);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Aquí podemos añadir la lógica de cierre de sesión.
        return ResponseEntity.ok("Tancament de sessió correcte");
    }
}
