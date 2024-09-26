package com.cibacoworking.cibacoworking.repositories;

import com.cibacoworking.cibacoworking.models.entities.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRol(String rol);
}
