package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.config.security.JwtUtil;
import com.cibacoworking.cibacoworking.models.dtos.AdminUserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.auth.LoginRequest;
import com.cibacoworking.cibacoworking.models.dtos.auth.LoginResponse;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private DTOMapper userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // Método para autenticar al usuario
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }

        String token = jwtUtil.generateToken(loginRequest.getEmail());
        User user = userService.getUserByEmail(loginRequest.getEmail());
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        LoginResponse loginResponse = new LoginResponse(token);
        return ResponseEntity.ok(loginResponse);
    }

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        UserDTO userDTO = userServices.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO) {
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = userServices.convertToEntity(userDTO);
        User savedUser = userServices.save(user);
        return new ResponseEntity<>(userServices.convertToDTO(savedUser), HttpStatus.CREATED);
    }

    // Obtener admin user por ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<AdminUserDTO> getAdminUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        AdminUserDTO adminUserDTO = userServices.convertToAdminDTO(user);
        return ResponseEntity.ok(adminUserDTO);
    }

    // Crear un admin user
    @PostMapping("/admin")
    public ResponseEntity<AdminUserDTO> createAdminUser(@Validated @RequestBody AdminUserDTO adminUserDTO) {
        User user = userServices.convertToEntity(adminUserDTO);
        User savedUser = userServices.save(user);
        return new ResponseEntity<>(userServices.convertToAdminDTO(savedUser), HttpStatus.CREATED);
    }

    // Método para cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
