package com.cibacoworking.cibacoworking.models.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    private Reservation reservation;
    private Space space; 
    private User user;

    @BeforeEach
    public void setUp() {
        space = new Space();
        space.setId(1);
        space.setName("Sala de reunions");

        user = new User();
        user.setId(1);
        user.setName("Manolo Díaz");

        reservation = new Reservation();
        reservation.setId(1);
        reservation.setStartDate(LocalDate.of(2024, 10, 5));
        reservation.setEndDate(LocalDate.of(2024, 10, 10));
        reservation.setStartTime(LocalTime.of(10, 0,0));
        reservation.setEndTime(LocalTime.of(12, 0,0));
        
        reservation.setSpace(space);
        reservation.setUser(user);
    }

    @Test
    public void testReservationProperties() {
        assertEquals(1, reservation.getId());
        assertEquals(LocalDate.of(2024, 10, 5), reservation.getStartDate());
        assertEquals(LocalDate.of(2024, 10, 10), reservation.getEndDate());
        assertEquals(LocalTime.of(10, 0,0), reservation.getStartTime());
        assertEquals(LocalTime.of(12, 0,0), reservation.getEndTime());
    }

}