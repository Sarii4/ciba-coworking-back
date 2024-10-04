package com.cibacoworking.cibacoworking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.dtos.requests.DateRangeRequestDTO;
import com.cibacoworking.cibacoworking.services.SpaceService;

@RestController
public class SpaceController {


    @Autowired
    private SpaceService spaceService;

    // Obtener todas las mesas disponibles con información de la mesa por fechas concretas
    @GetMapping(EndpointsConstants.GET_TABLES_BY_DATE)
    public ResponseEntity<List<SpaceDTO>> getAllAvailableTablesByDate(
            @RequestBody DateRangeRequestDTO dateRange) throws CibaCoworkingException {
        
        List<SpaceDTO> availableSpaces = spaceService.getAllAvailableTablesByDate(
            dateRange.getStartDate(), 
            dateRange.getEndDate(),
            dateRange.getStartTime(),
            dateRange.getEndTime());
        return ResponseEntity.ok(availableSpaces);
    }

    // Obtener todas las mesas con información de las reservas por fechas concretas
    @GetMapping(EndpointsConstants.GET_TABLES_BY_DATE_WITH_RESERVATIONS)
    public ResponseEntity<List<ReservationDTO>> getTablesWithReservations(
            @RequestBody DateRangeRequestDTO dateRange) throws CibaCoworkingException {
        
        List<ReservationDTO> tablesWithReservations = spaceService.getTablesWithReservations(
            dateRange.getStartDate(), 
            dateRange.getEndDate());
        return ResponseEntity.ok(tablesWithReservations);
    }

    //Obtener detalles de un espacio por su id
    @GetMapping(EndpointsConstants.GET_SPACE_BY_ID)
    public ResponseEntity<SpaceDTO> getSpaceById(@PathVariable int spaceId) {
        SpaceDTO spaceDTO = spaceService.getSpaceById(spaceId);
        return ResponseEntity.ok(spaceDTO);
    }

    
}
