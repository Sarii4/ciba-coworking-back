package com.cibacoworking.cibacoworking.models.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1);
        user.setName("Manolo Diaz");
        user.setEmail("manolodiaz@gmail.com");
    }
    @Test
    public void testUserProperties(){
        assertEquals(1, user.getId());
        assertEquals("Manolo Diaz", user.getName());
        assertEquals("manolodiaz@gmail.com", user.getEmail());
    }

}
