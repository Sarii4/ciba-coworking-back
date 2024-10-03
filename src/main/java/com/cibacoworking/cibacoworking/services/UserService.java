package com.cibacoworking.cibacoworking.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserRegistrationDTO;

public interface UserService {
    List<UserDTO> getAllUsers() throws CibaCoworkingException;
    UserDTO getUserById(int id) throws CibaCoworkingException;
    UserDTO createUser(UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException;
    UserDTO updateUser(int id, UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException;
    ResponseEntity<Object> deleteUser(int id) throws CibaCoworkingException;
    
}
