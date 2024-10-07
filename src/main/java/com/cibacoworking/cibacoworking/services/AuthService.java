package com.cibacoworking.cibacoworking.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.requests.LoginRequestDTO;
import com.cibacoworking.cibacoworking.models.dtos.responses.AuthResponseDTO;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.UserRepository;
import com.cibacoworking.cibacoworking.security.CustomUserDetailsService;
import com.cibacoworking.cibacoworking.security.jwt.JwtUtil;

@AllArgsConstructor
@Service
public class AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DTOMapper dtoMapper;

    public AuthResponseDTO login(LoginRequestDTO loginRequest) throws CibaCoworkingException {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword()));

            Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

            User user = optionalUser.get();

            UserDTO userDTO = dtoMapper.convertToDTO(user);

            String token = jwtUtil.generateToken(userDetails.getUsername());
            return new AuthResponseDTO(token, userDTO);
        } catch (Exception e) {
            throw new CibaCoworkingException("Credencials invàlides", HttpStatus.UNAUTHORIZED);
        }

    }

}
