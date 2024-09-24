package com.cibacoworking.cibacoworking.services;

import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DTOMapper dtoMapper;

     // Crear una reserva a largo plazo por administrador
     public ReservationDTO createLongTermReservation(ReservationDTO reservationDTO) throws CibaCoworkingException {
        // Lógica para manejar reservas de largo plazo
        if (reservationDTO.getStartDate().isAfter(reservationDTO.getEndDate())) {
            throw new CibaCoworkingException("La data d'inici no pot ser posterior a la data de finalització", HttpStatus.BAD_REQUEST);
        }
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoMapper.convertToDTO(savedReservation);
    }

    // Obtener espacios por ID y fechas concretas
    public List<ReservationDTO> getSpacesByIdAndDate(int spaceId, LocalDate startDate, LocalDate endDate) throws CibaCoworkingException {
        List<Reservation> availableSpaces = reservationRepository.findAvailableSpacesByIdAndDate(spaceId, startDate, endDate);
        return availableSpaces.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener todas las reservas de un usuario
    public List<ReservationDTO> getReservationsByUser(int userId) throws CibaCoworkingException {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        
        if (reservations.isEmpty()) {
            throw new CibaCoworkingException("No s'han trobat reserves per aquest usuari", HttpStatus.NOT_FOUND);
        }
        
        return reservations.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

   
    // Obtener una reserva por ID
    public ReservationDTO getReservationById(int id) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva amb aquest ID", HttpStatus.NOT_FOUND);
        }
        
        return dtoMapper.convertToDTO(reservationOpt.get());
    }

 

    // Crear una nueva reserva
    public ReservationDTO createReservation(ReservationDTO reservationDTO) throws CibaCoworkingException {
        try {
            Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
            Reservation savedReservation = reservationRepository.save(reservation);
            return dtoMapper.convertToDTO(savedReservation);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut crear la reserva", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  

    // Actualizar una reserva existente
    public ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva per actualitzar", HttpStatus.NOT_FOUND);
        }
    
        try {
            Reservation existingReservation = reservationOpt.get();
            // Actualizar los datos de la reserva
            existingReservation.setStartDate(reservationDTO.getStartDate());
            existingReservation.setEndDate(reservationDTO.getEndDate());
            existingReservation.setStartTime(reservationDTO.getStartTime());
            existingReservation.setEndTime(reservationDTO.getEndTime());
    
            Reservation updatedReservation = reservationRepository.save(existingReservation);
            return dtoMapper.convertToDTO(updatedReservation);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut actualitzar la reserva", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    

    // Eliminar una reserva 


    public ResponseEntity<Object> deleteReservation(int id) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva", HttpStatus.CONFLICT);
        }
        reservationRepository.delete(reservationOpt.get());
        return new ResponseEntity<>("S'ha eliminat amb èxit", HttpStatus.OK);
    }
}
