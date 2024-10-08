package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SpaceServiceImpl spaceService;
    private final DTOMapper dtoMapper;

    // OBTENER RESERVAS
    // Obtener reservas por ID de espacio y fechas concretas
    public List<ReservationDTO> getReservationsBySpaceAndDate(int spaceId, LocalDate startDate, LocalDate endDate)
            throws CibaCoworkingException {
        List<Reservation> reservationsBySpace = reservationRepository.findReservationsBySpaceAndDate(spaceId, startDate,
                endDate);
        if (reservationsBySpace == null || reservationsBySpace.size() == 0) {
            throw new CibaCoworkingException("Per aquesta data no hi ha reserves", HttpStatus.NOT_FOUND);
        }
        return reservationsBySpace.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener todas las reservas de un usuario por el id de usuario
    public List<ReservationDTO> getReservationsByUser(int userId) throws CibaCoworkingException {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);

        if (reservations.isEmpty()) {
            throw new CibaCoworkingException("No s'han trobat reserves per aquest usuari", HttpStatus.NOT_FOUND);
        }

        return reservations.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener una reserva por su ID
    public ReservationDTO getReservationById(int id) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva amb aquest ID", HttpStatus.NOT_FOUND);
        }

        return dtoMapper.convertToDTO(reservationOpt.get());
    }

    // CREAR RESERVAS
    // Crear una reserva a largo plazo por administrador de mesas individuales
    public ReservationDTO createLongTermReservation(ReservationDTO reservationDTO) throws CibaCoworkingException {

        ReservationPastDateValidator(reservationDTO.getEndDate());
        ReservationYearValidator(reservationDTO.getEndDate());

        boolean isTableActive = spaceService.checkTableStatus(reservationDTO.getSpaceDTO().getId());

        if (isTableActive) {
            Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
            spaceService.updateTableStatus(reservation.getSpace().getId(), "inactiu");
            Reservation savedReservation = reservationRepository.save(reservation);
            return dtoMapper.convertToDTO(savedReservation);
        } else {
            throw new CibaCoworkingException("Per aquesta taula ja existeix una reserva de llarga durada.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Crear una nueva reserva por oficinas y sala
    public ReservationDTO createReservationOffices(ReservationDTO reservationDTO) throws CibaCoworkingException {

        ReservationPastDateValidator(reservationDTO.getEndDate());
        ReservationYearValidator(reservationDTO.getEndDate());
        validateOfficeReservationDuration(reservationDTO.getStartDate(),
        reservationDTO.getEndDate());

        boolean isAvailable = isTableAvailable(reservationDTO.getSpaceDTO().getId(), reservationDTO.getStartDate(),
                reservationDTO.getEndDate(), reservationDTO.getStartTime(), reservationDTO.getEndTime());

        if (isAvailable) {
            try {
                Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
                Reservation savedReservation = reservationRepository.save(reservation);
                return dtoMapper.convertToDTO(savedReservation);
            } catch (Exception e) {
                throw new CibaCoworkingException("No s'ha pogut crear la reserva", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new CibaCoworkingException("Aquest espai ja està reservat per aquest periode.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    // Crear una nueva reserva de mesas individuales por el usuario
    public ReservationDTO createReservationTablesByUser(ReservationDTO reservationDTO) throws CibaCoworkingException {

        boolean isTableActive = spaceService.checkTableStatus(reservationDTO.getSpaceDTO().getId());

        ReservationPastDateValidator(reservationDTO.getEndDate());
        ReservationYearValidator(reservationDTO.getEndDate());
        validateTableReservationDuration(reservationDTO.getStartDate(), reservationDTO.getEndDate());

        boolean isAvailable = isTableAvailable(reservationDTO.getSpaceDTO().getId(), reservationDTO.getStartDate(),
                reservationDTO.getEndDate(), reservationDTO.getStartTime(), reservationDTO.getEndTime());

        if (isAvailable && isTableActive) {
            try {
                Reservation reservation = dtoMapper.convertToEntity(reservationDTO);
                Reservation savedReservation = reservationRepository.save(reservation);
                return dtoMapper.convertToDTO(savedReservation);
            } catch (Exception e) {
                throw new CibaCoworkingException("No s'ha pogut crear la reserva", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new CibaCoworkingException("La taula ja està reservada per aquest periode.", HttpStatus.BAD_REQUEST);
        }
    }

    // ACTUALIZAR una reserva existente
    public ReservationDTO updateReservation(int id, ReservationDTO reservationDTO) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva per actualitzar", HttpStatus.NOT_FOUND);
        }
        ReservationPastDateValidator(reservationDTO.getEndDate());
        ReservationYearValidator(reservationDTO.getEndDate());
        try {

            Reservation existingReservation = reservationOpt.get();
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
    // ELIMINAR una reserva
    public ResponseEntity<Object> deleteReservation(int id) throws CibaCoworkingException {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la reserva", HttpStatus.CONFLICT);
        }
        Reservation existingReservation = reservationOpt.get();

        boolean isTableActive = spaceService.checkTableStatus(existingReservation.getSpace().getId());

        try {
            if (!isTableActive) {
                spaceService.updateTableStatus(existingReservation.getSpace().getId(), "actiu");
            }
            reservationRepository.delete(existingReservation);
            return new ResponseEntity<>("S'ha eliminat amb èxit", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("No s'ha pogut eliminar la reserva", HttpStatus.BAD_REQUEST);
        }
    }
    // VALIDACIONES PARA RESERVAS
    // Verificar la disponibilidad de mesa individual
    public boolean isTableAvailable(int spaceId, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime) {
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(spaceId,
                startDate, endDate, startTime, endTime);
        return conflictingReservations.isEmpty();
    }
    // Validar que la reserva no puede superar el 31 de diciembre del año en curso
    public static void ReservationYearValidator(LocalDate endDate) throws CibaCoworkingException {
        LocalDate currentDate = LocalDate.now();
        LocalDate endOfYear = LocalDate.of(currentDate.getYear(), 12, 31);

        if (endDate.isAfter(endOfYear)) {
            throw new CibaCoworkingException(
                    "La data de finalització de la reserva no pot ser posterior al 31 de desembre de l'any actual.",
                    HttpStatus.BAD_REQUEST);
        }
    }
    // Validar que no se puede hacer la reserva si supera la fecha pasada
    public static void ReservationPastDateValidator(LocalDate endDate) throws CibaCoworkingException {
        LocalDate currentDate = LocalDate.now();
        if (endDate.isBefore(currentDate)) {
            throw new CibaCoworkingException("No es pot fer una reserva per una data ja passada.",
                    HttpStatus.BAD_REQUEST);
        }
    }
    // Validar que la duración de la reserva de la mesa puede ser como máximo 7 días
    public static void validateTableReservationDuration(LocalDate startDate, LocalDate endDate)
            throws CibaCoworkingException {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        if (daysBetween > 7) {
            throw new CibaCoworkingException(
                    "La reserva no pot ser per un període superior a 7 dies. Si necesites un període més llarg consulta a l'administrador",
                    HttpStatus.BAD_REQUEST);
        }
    }
    // Validar que la duración de la reserva de oficinas o sala tiene que ser hecha para un día sin permitir periodos
    public static void validateOfficeReservationDuration(LocalDate startDate, LocalDate endDate)
            throws CibaCoworkingException {

        if (!startDate.equals(endDate)) {
            throw new CibaCoworkingException(
                    "La reserva només pot ser d´un día.",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
