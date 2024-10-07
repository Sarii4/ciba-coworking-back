package com.cibacoworking.cibacoworking.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.models.entities.User;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SpaceRepository spaceRepository; 
    @Autowired
    private UserRepository userRepository;  

    private Space Space;
    private User User;

    @BeforeEach
    public void setUp() {

        Space = new Space();
        Space.setName(" D2");
        Space.setSpaceType("despatx");
        Space.setSpaceStatus("actiu");
        Space.setDescription("descipció");
        Space = spaceRepository.save(Space); 

        User = new User();
        User.setName("Manolo Diaz");
        User.setEmail("manolodiaz@gmail.com");
        User.setPassword("password");
        User.setPhone("1234567890");
        User.setProjectName("CDigital");
        User = userRepository.save(User); 
    }

    @Test
    @Rollback(false) 
    public void testFindByUserId() {

        Reservation reservation = new Reservation();
        reservation.setSpace(Space);
        reservation.setUser(User);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setStartTime(LocalTime.of(10, 0,0));
        reservation.setEndTime(LocalTime.of(12, 0,0));
        reservationRepository.save(reservation);

        List<Reservation> foundReservations = reservationRepository.findByUserId(User.getId());

        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getUser().getId()).isEqualTo(User.getId());
    }

    @Test
    public void testFindReservationsBySpaceAndDate() {

        Reservation reservation = new Reservation();
        reservation.setSpace(Space);
        reservation.setUser(User);
        reservation.setStartDate(LocalDate.now());
        reservation.setEndDate(LocalDate.now().plusDays(1));
        reservation.setStartTime(LocalTime.of(10, 0,0));
        reservation.setEndTime(LocalTime.of(12, 0,0));
        reservationRepository.save(reservation);

        // Ejecutar el método
        List<Reservation> foundReservations = reservationRepository.findReservationsBySpaceAndDate(
                Space.getId(), LocalDate.now(), LocalDate.now().plusDays(1));

        // Verificar resultados
        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getSpace().getId()).isEqualTo(Space.getId());
    }

    @Test
    public void testFindConflictingReservations() {
        // Agregar reservas de prueba
        Reservation reservation1 = new Reservation();
        reservation1.setSpace(Space);
        reservation1.setUser(User);
        reservation1.setStartDate(LocalDate.now());
        reservation1.setEndDate(LocalDate.now().plusDays(1));
        reservation1.setStartTime(LocalTime.of(10, 0,0));
        reservation1.setEndTime(LocalTime.of(12, 0,0));
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setSpace(Space);
        reservation2.setUser(User);
        reservation2.setStartDate(LocalDate.now());
        reservation2.setEndDate(LocalDate.now().plusDays(1));
        reservation2.setStartTime(LocalTime.of(11, 0,0)); // Superpone con reservation1
        reservation2.setEndTime(LocalTime.of(13, 0,0));
        reservationRepository.save(reservation2);

        // Ejecutar el método
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(
                Space.getId(), LocalDate.now(), LocalDate.now().plusDays(1),
                LocalTime.of(10, 0), LocalTime.of(12, 0,0));

        // Verificar resultados
        assertThat(conflictingReservations).isNotEmpty(); // Debería haber conflictos
    }

    @Test
    public void testFindByEndDateBefore() {
        // Agregar una reserva de prueba
        Reservation reservation = new Reservation();
        reservation.setSpace(Space);
        reservation.setUser(User);
        reservation.setStartDate(LocalDate.now().minusDays(3));
        reservation.setEndDate(LocalDate.now().minusDays(1));
        reservation.setStartTime(LocalTime.of(10, 0,0));
        reservation.setEndTime(LocalTime.of(12, 0,0));
        reservationRepository.save(reservation);

        // Ejecutar el método
        List<Reservation> foundReservations = reservationRepository.findByEndDateBefore(LocalDate.now());

        // Verificar resultados
        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getEndDate()).isBefore(LocalDate.now());
    }
}