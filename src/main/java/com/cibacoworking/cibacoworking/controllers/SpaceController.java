package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.models.entities.Reservation; // Asegúrate de tener esta clase
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO; // DTO para reservas
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository; // Asegúrate de tener este repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private DTOMapper dtoMapper;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ReservationRepository reservationRepository; // Para gestionar reservas

    // Obtener espacio por ID
    @GetMapping("/{id}")
    public ResponseEntity<SpaceDTO> getSpaceById(@PathVariable int id) {
        Space space = spaceRepository.findById(id).orElse(null);
        if (space == null) {
            return ResponseEntity.notFound().build();
        }
        SpaceDTO spaceDTO = dtoMapper.convertToDTO(space);
        return ResponseEntity.ok(spaceDTO);
    }

    // Obtener todos los espacios
    @GetMapping
    public ResponseEntity<List<SpaceDTO>> getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();
        List<SpaceDTO> spaceDTOs = dtoMapper.convertToDTOs(spaces);
        return ResponseEntity.ok(spaceDTOs);
    }

    // Reservar un espacio existente
    @PostMapping("/{spaceId}/reserve")
    public ResponseEntity<ReservationDTO> reserveSpace(@PathVariable int spaceId, @RequestBody ReservationDTO reservationDTO) {
        Space space = spaceRepository.findById(spaceId).orElse(null);
        if (space == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Aquí puedes agregar lógica para verificar disponibilidad, si es necesario
        
        Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
        reservation.setSpace(space); // Asociar la reserva al espacio
        Reservation savedReservation = reservationRepository.save(reservation); // Guardar la reserva
        
        ReservationDTO savedReservationDTO = dtoMapper.convertToDTO(savedReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReservationDTO);
    }
}
