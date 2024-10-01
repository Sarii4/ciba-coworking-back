package com.cibacoworking.cibacoworking.models.entities;

import com.cibacoworking.cibacoworking.models.dtos.RegisterRequest;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 60, nullable = false) 
    private String password;

    @Column(length = 15)
    private String phone;

    @Column(name = "project_name", length = 50)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Role role;

    // Constructor vacío
    public User() {}

    // Constructor para inicializar User desde RegisterRequest
    public User(RegisterRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.phone = request.getPhone(); // Si tienes un campo phone en RegisterRequest
        this.projectName = request.getProjectName(); // Si tienes un campo projectName
    }

    // Getters y Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserDTO toUserDTO() {
        return new UserDTO(this.id, this.name, this.email, this.phone, this.projectName, this.role.getRol());
    }

    // Métodos implementados de la interfaz UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRol()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Método toString (opcional, pero recomendable para depuración)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", projectName='" + projectName + '\'' +
                ", role=" + role +
                '}';
    }

    // Método equals (opcional, útil si necesitas comparar usuarios)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }

    // Método hashCode (opcional, útil si necesitas almacenar usuarios en colecciones)
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
