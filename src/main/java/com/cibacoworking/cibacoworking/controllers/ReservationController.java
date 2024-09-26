package com.cibacoworking.cibacoworking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibacoworking.cibacoworking.config.ConstantsSecurity;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DateRangeRequestDTO;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.services.ReservationService;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // User reservations

    // Crear reserva por usuario
    /*
     * @PostMapping(ConstantsSecurity.CREATE_RESERVATION_BY_USER)
     * public ResponseEntity<ReservationDTO> createReservation(@RequestBody
     * ReservationDTO reservationDTO) throws CibaCoworkingException {
     * ReservationDTO savedReservation =
     * reservationService.createReservation(reservationDTO);
     * return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
     * }
     */
    // Crear reserva mesas
    @PostMapping(ConstantsSecurity.CREATE_RESERVATION_TABLES)
    public ResponseEntity<ReservationDTO> createReservationTables(@RequestBody ReservationDTO reservationDTO)
            throws CibaCoworkingException {
        ReservationDTO savedReservation = reservationService.createReservationTables(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    // Crear reserva oficinas y sala

    @PostMapping(ConstantsSecurity.CREATE_RESERVATION_OFFICES)
    public ResponseEntity<ReservationDTO> createReservationOffices(@RequestBody ReservationDTO reservationDTO)
            throws CibaCoworkingException {
        ReservationDTO savedReservation = reservationService.createReservationOffices(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    // Crear reserva a largo plazo por administrador
    @PostMapping(ConstantsSecurity.CREATE_LONG_TERM_RESERVATION_ADMIN)
    public ResponseEntity<ReservationDTO> createLongTermReservation(
        @RequestBody ReservationDTO reservationDTO) throws CibaCoworkingException {
        ReservationDTO savedReservation = reservationService.createLongTermReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    // Ver todas las reservas por usuario
    @GetMapping(ConstantsSecurity.GET_RESERVATIONS_BY_USER)
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable int userId)
            throws CibaCoworkingException {
        List<ReservationDTO> reservationDTOs = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservationDTOs);
    }

    // Ver detalles de una reserva por id
    @GetMapping(ConstantsSecurity.GET_RESERVATION_BY_ID)
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable int reservationId)
            throws CibaCoworkingException {
        ReservationDTO reservationDTO = reservationService.getReservationById(reservationId);
        return ResponseEntity.ok(reservationDTO);
    }

    // Obtener reservas por ID de espacio y fechas concretas
    @GetMapping(ConstantsSecurity.GET_RESERVATIONS_BY_SPACE_AND_DATE)
    public ResponseEntity<List<ReservationDTO>> getReservationsBySpaceAndDate(
            @PathVariable int spaceId,
            @RequestBody DateRangeRequestDTO dateRange) throws CibaCoworkingException {

        List<ReservationDTO> availableSpaces = reservationService.getReservationsBySpaceAndDate(spaceId,
                dateRange.getStartDate(), dateRange.getEndDate());
        return ResponseEntity.ok(availableSpaces);
    }

    // Actualizar reserva
    @PutMapping(ConstantsSecurity.UPDATE_RESERVATION)
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable int reservationId,
            @RequestBody ReservationDTO reservationDTO) throws CibaCoworkingException {

        ReservationDTO updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReservation);
    }

    // Eliminar reserva
    @DeleteMapping(ConstantsSecurity.DELETE_RESERVATION)
    public ResponseEntity<String> deleteReservation(@PathVariable int reservationId) throws CibaCoworkingException {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("S'ha eliminat amb èxit");
    }
}
