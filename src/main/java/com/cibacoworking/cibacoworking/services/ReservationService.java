package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;

public interface ReservationService {
    List<ReservationDTO> getReservationsBySpaceAndDate(int spaceId, LocalDate startDate, LocalDate endDate) throws CibaCoworkingException;
    List<ReservationDTO> getReservationsByUser(int userId) throws CibaCoworkingException;
    ReservationDTO getReservationById(int id) throws CibaCoworkingException;
    ReservationDTO createLongTermReservation(ReservationDTO reservationDTO) throws CibaCoworkingException;
    ReservationDTO createReservationOffices(ReservationDTO reservationDTO) throws CibaCoworkingException;
    ReservationDTO createReservationTablesByUser(ReservationDTO reservationDTO) throws CibaCoworkingException ;
    ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) throws CibaCoworkingException;
    ResponseEntity<Object> deleteReservation(int id) throws CibaCoworkingException;
    
} 
