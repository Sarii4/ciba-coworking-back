package com.cibacoworking.cibacoworking.services;

import com.cibacoworking.cibacoworking.models.LoginRequest;
import com.cibacoworking.cibacoworking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService; 
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager; 

    public String authenticate(LoginRequest loginRequest) throws Exception {
        // Intenta autenticar al usuario con la contraseña
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()) 
            );
        } catch (Exception e) {
            throw new Exception("Autenticación fallida: " + e.getMessage());
        }
        
        // Si la autenticación es exitosa, carga los detalles del usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail()); 

        // Genera el token
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
