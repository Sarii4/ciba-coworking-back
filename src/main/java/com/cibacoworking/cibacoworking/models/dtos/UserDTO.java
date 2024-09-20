package com.cibacoworking.cibacoworking.models.dtos;

public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String projectName;
    private String role; 

    public UserDTO() {}

    public UserDTO(int id, String name, String email, String phone, String projectName, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.projectName = projectName;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
