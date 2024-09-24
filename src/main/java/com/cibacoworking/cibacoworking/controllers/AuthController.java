package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.Login;
import com.cibacoworking.cibacoworking.models.LoginRequest;
import com.cibacoworking.cibacoworking.models.BodyErrorMessage;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.logging.Logger;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Value("${JWT_SECRET_KEY}") // Carga la clave secreta desde el archivo .env
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 86400000; 
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticate(loginRequest); 
            String token = generateToken(user); 
            Login loginResponse = new Login(user, token, true);
            logger.info("L'usuari " + user.getEmail() + " ha iniciat sessió correctament.");
            return ResponseEntity.ok(loginResponse);
        } catch (CibaCoworkingException e) {
            BodyErrorMessage errorMessage = new BodyErrorMessage();
            errorMessage.setHttpStatus(401);
            errorMessage.setMessage("Autenticació fallida: " + e.getMessage());
            logger.warning("Intent de connexió fallit per l'usuari " + loginRequest.getEmail() + ": " + e.getMessage());
            return ResponseEntity.status(401).body(errorMessage);
        } catch (Exception e) {
            BodyErrorMessage errorMessage = new BodyErrorMessage();
            errorMessage.setHttpStatus(500);
            errorMessage.setMessage("Error inesperat: " + e.getMessage());
            logger.severe("Error inesperat durant la connexió: " + e.getMessage());
            return ResponseEntity.status(500).body(errorMessage);
        }
    }

    private String generateToken(User user) {
    
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole().getRol()); 

     
        SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); 

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) 
                .signWith(secretKey, SignatureAlgorithm.HS256) 
                .compact();
    }
}
