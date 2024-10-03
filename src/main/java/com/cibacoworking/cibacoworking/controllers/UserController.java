
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

    @GetMapping(ConstantsSecurity.GET_USER_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) throws CibaCoworkingException {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(ConstantsSecurity.CREATE_USER)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException {
        UserDTO savedUser = userService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping(ConstantsSecurity.DELETE_USER)
    public ResponseEntity<String> deleteUser(@PathVariable int userId) throws CibaCoworkingException {
        userService.deleteUser(userId);
        return ResponseEntity.ok("S'ha eliminat amb èxit");
    }

    @PutMapping(ConstantsSecurity.UPDATE_USER)
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable int userId,
            @RequestBody UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException {

        UserDTO updatedUser = userService.updateUser(userId, userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}