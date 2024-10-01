package com.cibacoworking.cibacoworking.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cibacoworking.cibacoworking.models.dtos.RegisterRequest;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.UserRepository;
import com.cibacoworking.cibacoworking.jwt.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager; 
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        UserDetails userDetails = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), request.getPassword())
        );

        String token = jwtUtil.generateToken(userDetails.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); 
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    // Métodos adicionales si decides mantenerlos
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        return user;
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getEmail());
    }
}
