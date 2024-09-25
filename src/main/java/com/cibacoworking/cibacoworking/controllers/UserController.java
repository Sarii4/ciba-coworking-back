package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.dtos.AdminUserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private DTOMapper userServices;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        return userServices.convertToDTO(user);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = userServices.convertToEntity(userDTO);
        User savedUser = userServices.save(user);
        return userServices.convertToDTO(savedUser);
    }

    @GetMapping("/admin/{id}")
    public AdminUserDTO getAdminUserById(@PathVariable int id) {
        User user = userServices.findById(id);
        return userServices.convertToAdminDTO(user);
    }

    @PostMapping("/admin")
    public AdminUserDTO createAdminUser(@RequestBody AdminUserDTO adminUserDTO) {
        User user = userServices.convertToEntity(adminUserDTO);
        User savedUser = userServices.save(user);
        return userServices.convertToAdminDTO(savedUser);
    }
}