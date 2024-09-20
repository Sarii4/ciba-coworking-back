package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private DTOMapper dtoMapper;

    @Autowired
    private ReservationRepository reservationRepository; 

    @GetMapping("/{id}")
    public ReservationDTO getReservationById(@PathVariable int id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        return dtoMapper.convertToDTO(reservation);
    }

    @PostMapping
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoMapper.convertToDTO(savedReservation);
    }
}
