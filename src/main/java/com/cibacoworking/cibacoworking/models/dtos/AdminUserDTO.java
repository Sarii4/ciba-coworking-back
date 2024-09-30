package com.cibacoworking.cibacoworking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AdminUserDTO {
    private int id;
    private String name;
    private String email;
    private String password; // Información sensible solo para el Admin
    private String phone;
    private String projectName;
    private String role; 
}
