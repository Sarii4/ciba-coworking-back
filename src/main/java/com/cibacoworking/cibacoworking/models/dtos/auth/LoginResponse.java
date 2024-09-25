package com.cibacoworking.cibacoworking.models.dtos.auth;

import com.cibacoworking.cibacoworking.models.dtos.UserDTO;

public class LoginResponse {
    private String token;
    private UserDTO userDTO;
    private boolean success; 

    public LoginResponse(String token, UserDTO userDTO, boolean success) {
        this.token = token;
        this.userDTO = userDTO;
        this.success = success;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
