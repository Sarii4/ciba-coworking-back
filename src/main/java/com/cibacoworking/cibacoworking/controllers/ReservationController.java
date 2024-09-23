package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.services.ReservationService;
/* import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;  */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.config.ConstantsSecurity;

import java.util.List;

@RestController
public class ReservationController {

   @Autowired
    private ReservationService reservationService;

    //User reservations
    
    //Crear reserva por usuario
    @PostMapping(ConstantsSecurity.CREATE_RESERVATION_BY_USER)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationDTO savedReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

    //Ver todas las reservas por usuario
     @GetMapping(ConstantsSecurity.GET_RESERVATIONS_BY_USER)
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable int userId) {
        List<ReservationDTO> reservationDTOs = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservationDTOs);
    }
    
   /*  @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable int id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return dtoMapper.convertToDTO(reservation);
    } */

    //Ver reserva específica por Id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable int id) {
        ReservationDTO reservationDTO = reservationService.getReservationById(id);
        if (reservationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationDTO);
    }

    //Crear reserva

   /*  @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoMapper.convertToDTO(savedReservation);
    } */
   
    //Actualizar reserva
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable int id, @RequestBody ReservationDTO reservationDTO) {
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservationDTO);
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReservation);
    }

    //Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable int id) throws CibaCoworkingException {
       return reservationService.deleteReservation(id);

    }
}
//En teoria está ok
