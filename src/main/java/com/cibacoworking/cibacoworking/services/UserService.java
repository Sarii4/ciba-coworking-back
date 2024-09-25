package com.cibacoworking.cibacoworking.services;

import com.cibacoworking.cibacoworking.models.dtos.auth.LoginRequest;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.UserRepository;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())  // El email se usa como "username"
                .password(user.getPassword())
                .authorities(user.getRole().getRol())  // Asegúrate de que el rol se devuelve correctamente
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public User authenticate(LoginRequest loginRequest) throws CibaCoworkingException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CibaCoworkingException("Credenciales inválidas."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CibaCoworkingException("Credenciales inválidas.");
        }

        return user;  // Retorna el usuario autenticado
    }
}
