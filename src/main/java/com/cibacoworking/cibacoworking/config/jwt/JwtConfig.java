package com.cibacoworking.cibacoworking.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cibacoworking.cibacoworking.services.UserServiceImpl;


@Configuration
public class JwtConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, UserServiceImpl userServiceImpl) {
        return new JwtAuthenticationFilter(jwtUtil, userServiceImpl);
    }
}