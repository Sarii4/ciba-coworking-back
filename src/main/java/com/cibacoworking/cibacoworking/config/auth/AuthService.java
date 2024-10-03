package com.cibacoworking.cibacoworking.config.auth;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.cibacoworking.cibacoworking.services.UserServiceImpl;
import com.cibacoworking.cibacoworking.config.jwt.JwtUtil;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DTOMapper dtoMapper;

    public AuthResponse login(LoginRequest loginRequest) throws CibaCoworkingException {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(loginRequest.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword()));

            Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

            User user = optionalUser.get();

            UserDTO userDTO = dtoMapper.convertToDTO(user);

            String token = jwtUtil.generateToken(userDetails.getUsername());
            return new AuthResponse(token, userDTO);
        } catch (Exception e) {
            throw new CibaCoworkingException("Credencials invàlides", HttpStatus.UNAUTHORIZED);
        }

    }

    // falta logica logout
}
