package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.config.security.JwtUtil;
import com.cibacoworking.cibacoworking.models.dtos.auth.Login; 
import com.cibacoworking.cibacoworking.models.dtos.AdminUserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.auth.LoginRequest;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        UserDTO userDTO = userServices.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO) {
        if (userDTO.getRole() == null || userDTO.getRole().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = userServices.convertToEntity(userDTO);
        User savedUser = userServices.save(user);
        return new ResponseEntity<>(userServices.convertToDTO(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<AdminUserDTO> getAdminUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        AdminUserDTO adminUserDTO = userServices.convertToAdminDTO(user);
        return ResponseEntity.ok(adminUserDTO);
    }

    @PostMapping("/admin")
    public ResponseEntity<AdminUserDTO> createAdminUser(@Validated @RequestBody AdminUserDTO adminUserDTO) {
        User user = userServices.convertToEntity(adminUserDTO);
        User savedUser = userServices.save(user);
        return new ResponseEntity<>(userServices.convertToAdminDTO(savedUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Login> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
        }

        String token = jwtUtil.generateToken(loginRequest.getEmail());

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
        User user = new User();
        user.setId(((User) userDetails).getId());
        user.setEmail(userDetails.getUsername());

        Login loginResponse = new Login(user, token, true);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout successful");
    }
}
