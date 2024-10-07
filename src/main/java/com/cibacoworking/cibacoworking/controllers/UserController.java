
package com.cibacoworking.cibacoworking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.requests.UserRegistrationRequestDTO;
import com.cibacoworking.cibacoworking.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(EndpointsConstants.GET_ALL_USERS)
    public ResponseEntity<List<UserDTO>> getAllUsers() throws CibaCoworkingException {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping(EndpointsConstants.GET_USER_BY_ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable int userId) throws CibaCoworkingException {
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping(EndpointsConstants.CREATE_USER)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationRequestDTO userRegistrationDTO) throws CibaCoworkingException {
        UserDTO savedUser = userService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping(EndpointsConstants.DELETE_USER)
    public ResponseEntity<String> deleteUser(@PathVariable int userId) throws CibaCoworkingException {
        userService.deleteUser(userId);
        return ResponseEntity.ok("S'ha eliminat amb èxit");
    }

    @PutMapping(EndpointsConstants.UPDATE_USER)
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable int userId,
            @RequestBody UserRegistrationRequestDTO userRegistrationDTO) throws CibaCoworkingException {

        UserDTO updatedUser = userService.updateUser(userId, userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}