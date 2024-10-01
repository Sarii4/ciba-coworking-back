package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;

public interface SpaceService {
    List<SpaceDTO> getAllAvailableTablesByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) throws CibaCoworkingException; 
    SpaceDTO getSpaceById(int spaceId);
    boolean checkTableStatus(int spaceId);
    void updateTableStatus(int id, String newStatus) throws CibaCoworkingException;
    List<ReservationDTO> getTablesWithReservations(LocalDate startDate, LocalDate endDate) throws CibaCoworkingException;
    void updateExpiredReservations() throws CibaCoworkingException;
}
