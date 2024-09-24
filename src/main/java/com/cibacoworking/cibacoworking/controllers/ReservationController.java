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
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservationController {

   @Autowired
    private ReservationService reservationService;

    //User reservations
    
    //Crear reserva por usuario
    @PostMapping(ConstantsSecurity.CREATE_RESERVATION_BY_USER)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws CibaCoworkingException {
        ReservationDTO savedReservation = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
    }

      // Crear reserva a largo plazo por administrador
      @PostMapping(ConstantsSecurity.CREATE_LONG_TERM_RESERVATION_ADMIN)
      public ResponseEntity<ReservationDTO> createLongTermReservation(
              @RequestBody ReservationDTO reservationDTO) throws CibaCoworkingException {
          ReservationDTO savedReservation = reservationService.createLongTermReservation(reservationDTO);
          return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
      }
  

    //Ver todas las reservas por usuario
     @GetMapping(ConstantsSecurity.GET_RESERVATIONS_BY_USER)
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable int userId) throws CibaCoworkingException {
        List<ReservationDTO> reservationDTOs = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservationDTOs);
    }

    // Obtener espacios por ID y fechas concretas
    @GetMapping(ConstantsSecurity.GET_SPACES_BY_ID_AND_DATE)
    public ResponseEntity<List<ReservationDTO>> getSpacesByIdAndDate(
            @PathVariable int spaceId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) throws CibaCoworkingException {
        
        List<ReservationDTO> availableSpaces = reservationService.getSpacesByIdAndDate(spaceId, startDate, endDate);
        return ResponseEntity.ok(availableSpaces);
    }
    
   /*  @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable int id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return dtoMapper.convertToDTO(reservation);
    } */

    //Ver reserva específica por Id
   /*  @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable int id) {
        ReservationDTO reservationDTO = reservationService.getReservationById(id);
        if (reservationDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationDTO);
    }
 */
    //Crear reserva

   /*  @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoMapper.convertToDTO(savedReservation);
    } */
   
    //Actualizar reserva
    @PutMapping(ConstantsSecurity.UPDATE_RESERVATION)
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable int reservationId, 
            @RequestBody ReservationDTO reservationDTO) throws CibaCoworkingException {
        
        ReservationDTO updatedReservation = reservationService.updateReservation(reservationId, reservationDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReservation);
    }
  

    //Eliminar reserva
    @DeleteMapping(ConstantsSecurity.DELETE_RESERVATION)
    public ResponseEntity<String> deleteReservation(@PathVariable int reservationId) throws CibaCoworkingException {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("S'ha eliminat amb èxit");
    }
    

}
//En teoria está ok
