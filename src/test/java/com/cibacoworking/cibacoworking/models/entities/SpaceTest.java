package com.cibacoworking.cibacoworking.models.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpaceTest {
    private Space space;

    @BeforeEach
    public void setUp(){
        space = new Space();
        space.setId(1);
        space.setName("Sala de reunions");
    }

    @Test
    public void testSpaceProperties (){
        assertEquals(1,space.getId());
        assertEquals("Sala de reunions",space.getName());
    }
}
