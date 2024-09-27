package com.cibacoworking.cibacoworking.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        // Crea una instancia de BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Contraseña que deseas codificar
        String rawPassword = "password10";

        // Codifica la contraseña
        String encodedPassword = encoder.encode(rawPassword);

        // Imprime la contraseña codificada
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
