// package com.cibacoworking.cibacoworking.repositories;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.cibacoworking.cibacoworking.models.entities.Reservation;
// import com.cibacoworking.cibacoworking.models.entities.Space;
// import com.cibacoworking.cibacoworking.models.entities.User;

// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @ActiveProfiles("test")

// class ReservationRepositoryTest {

//     @Autowired
//     private ReservationRepository reservationRepository;

//     @Autowired
//     private SpaceRepository spaceRepository;

//     @Autowired
//     private UserRepository userRepository;

//     @Test
//     void testFindByUserId() {

//         Space space = new Space();
//         space.setName("Oficina 1");
//         space.setSpaceType("D1");
//         space.setSpaceStatus("actiu");
//         space.setDescription("description");
//         Space savedSpace = spaceRepository.save(space);

//         User user = new User();
//         user.setName("Sara");
//         user.setEmail("sara@gmail.com");
//         user.setPhone("654321987");
//         user.setProjectName("Ess");
//         user.setPassword("password1234");
//         User savedUser = userRepository.save(user);

//         Reservation reservation = new Reservation();
//         reservation.setSpace(savedSpace); 
//         reservation.setUser(savedUser); 
//         reservation.setStartDate(LocalDate.of(2024, 10, 20));
//         reservation.setEndDate(LocalDate.of(2024, 10, 20));
//         reservation.setStartTime(LocalTime.of(10, 0, 0));
//         reservation.setEndTime(LocalTime.of(11, 0, 0));
//         Reservation savedReservation = reservationRepository.save(reservation);

//         Optional<Reservation> foundReservation = reservationRepository.findById(savedReservation.getId());
//         assertTrue(foundReservation.isPresent()); 

//         assertThat(foundReservation.get().getSpace().getName()).isEqualTo("Oficina 1");

//         assertThat(foundReservation.get().getId()).isEqualTo(savedReservation.getId());
//     }

// }