package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;
    private final DTOMapper dtoMapper;

    // Obtener todas las mesas disponibles con información completa por fechas concretas   
    public List<SpaceDTO> getAllAvailableTablesByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime) throws CibaCoworkingException {
        List<Space> tablesByDate = spaceRepository.findAvailableTablesByDate(startDate, endDate, startTime, endTime);
        return tablesByDate.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
    // Obtener las mesas reservadas e inactivas con información de las reservas por fechas concretas
    public List<ReservationDTO> getTablesWithReservations(LocalDate startDate, LocalDate endDate)
            throws CibaCoworkingException {
        List<Reservation> tablesWithReservations = spaceRepository.findTablesWithReservations(startDate, endDate);

        return tablesWithReservations.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }
    // Obtener detalles de un espacio por su Id
    public SpaceDTO getSpaceById(int spaceId) {
        Space space = spaceRepository.findById(spaceId).orElse(null);
        return dtoMapper.convertToDTO(space);
    }
    // Comprobar el estatus de una mesa por su Id
    public boolean checkTableStatus(int spaceId) {
        String tableStatus = spaceRepository.findSpaceStatusById(spaceId);
        return "actiu".equals(tableStatus);
    }
    // Cambiar el estatus de una mesa por su Id
    public void updateTableStatus(int id, String newStatus) throws CibaCoworkingException {
        Optional<Space> spaceOpt = spaceRepository.findById(id);
        if (!spaceOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la taula per actualitzar", HttpStatus.NOT_FOUND);
        }
        try {
            Space existingTable = spaceOpt.get();
            existingTable.setSpaceStatus(newStatus);
            spaceRepository.save(existingTable);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut actualitzar l'estat de la taula",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Programar caducidad del status 'inactiu' de mesa individual
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredReservations() throws CibaCoworkingException {
        LocalDate today = LocalDate.now();

        List<Reservation> expiredReservations = reservationRepository.findByEndDateBefore(today);

        for (Reservation reservation : expiredReservations) {
            boolean isTableInactive = !checkTableStatus(reservation.getSpace().getId());

            if (isTableInactive) {
                updateTableStatus(reservation.getSpace().getId(), "actiu");
            }
        }
    }

}
