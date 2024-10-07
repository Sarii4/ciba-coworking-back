package com.cibacoworking.cibacoworking.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cibacoworking.cibacoworking.models.entities.Role;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setRol("ADMIN");
    }

    @Test
    public void testRoleProperties() {
        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getRol());
    }
}