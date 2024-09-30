
package com.cibacoworking.cibacoworking.controllers;


import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserRegistrationDTO;
import com.cibacoworking.cibacoworking.services.UserService;
import com.cibacoworking.cibacoworking.config.ConstantsSecurity;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
      

    @GetMapping(ConstantsSecurity.GET_ALL_USERS)
    public ResponseEntity<List<UserDTO>> getAllUsers() throws CibaCoworkingException {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    /* 
    @GetMapping(ConstantsSecurity.GET_ALL_USERS)
    public UserDTO getAllUser(@PathVariable int id) {
        User user = userServices.findById(id);
        return userServices.convertToDTO(user);
    } */

    @PostMapping(ConstantsSecurity.CREATE_USER)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException {
        UserDTO savedUser = userService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
       
    }

   

    /* @GetMapping("/admin/{id}")
    public AdminUserDTO getAdminUserById(@PathVariable int id) {
        User user = userService.findById(id);
        return userService.convertToAdminDTO(user);
    }

    @PostMapping("/admin")
    public AdminUserDTO createAdminUser(@RequestBody AdminUserDTO adminUserDTO) {
        User user = userService.convertToEntity(adminUserDTO);
        User savedUser = userService.save(user);
        return userService.convertToAdminDTO(savedUser);
    } */
}