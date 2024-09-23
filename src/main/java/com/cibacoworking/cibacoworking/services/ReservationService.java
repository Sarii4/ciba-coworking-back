package com.cibacoworking.cibacoworking.services;

import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    // Obtener todas las reservas de un usuario
    public List<ReservationDTO> getReservationsByUser(int userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener una reserva por ID
    public ReservationDTO getReservationById(int id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            return dtoMapper.convertToDTO(reservationOpt.get());
        }
        return null; // O podrías lanzar una excepción personalizada
    }

    // Crear una nueva reserva
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoMapper.convertToDTO(savedReservation);
    }

    // Actualizar una reserva existente
    public ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (reservationOpt.isPresent()) {
            Reservation existingReservation = reservationOpt.get();
            // Actualizar los datos de la reserva
            existingReservation.setStartDate(reservationDTO.getStartDate());
            existingReservation.setEndDate(reservationDTO.getEndDate());
            existingReservation.setStartTime(reservationDTO.getStartTime());
            existingReservation.setEndTime(reservationDTO.getEndTime());
            
            Reservation updatedReservation = reservationRepository.save(existingReservation);
            return dtoMapper.convertToDTO(updatedReservation);
        }
        return null; // O lanzar una excepción personalizada VERIFICAR
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
