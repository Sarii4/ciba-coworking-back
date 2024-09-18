// package com.cdigital.cdigital_backend.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.cdigital.cdigital_backend.security.JwtUtil;
// import com.cdigital.cdigital_backend.services.CustomUserDetailsService;

// @Configuration
// public class JwtConfig {
//     @Bean
//     public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
//         return new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
//     }
// }