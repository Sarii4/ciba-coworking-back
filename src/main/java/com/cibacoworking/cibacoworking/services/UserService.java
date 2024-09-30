package com.cibacoworking.cibacoworking.services;

import java.util.List;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserRegistrationDTO;

public interface UserService {
    List<UserDTO> getAllUsers() throws CibaCoworkingException;
    UserDTO createUser(UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException;
}
