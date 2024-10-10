package com.cibacoworking.cibacoworking.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;

@SpringBootTest
public class SpaceRepositoryTest {

    @Mock
    private SpaceRepository spaceRepository;

    @InjectMocks
    private SpaceRepositoryTest spaceRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindSpaceStatusById() {
        int spaceId = 1;
        String expectedStatus = "actiu";

        when(spaceRepository.findSpaceStatusById(spaceId)).thenReturn(expectedStatus);

        String status = spaceRepository.findSpaceStatusById(spaceId);

        assertThat(status).isEqualTo(expectedStatus);
    }

    @Test
    void testFindAvailableTablesByDate() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 10);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        Space space1 = new Space();
        space1.setId(4);
        space1.setName("Table 1");
        space1.setSpaceStatus("actiu");

        Space space2 = new Space();
        space2.setId(5);
        space2.setName("Table 2");
        space2.setSpaceStatus("actiu");

        when(spaceRepository.findAvailableTablesByDate(startDate, endDate, startTime, endTime))
                .thenReturn(Arrays.asList(space1, space2));

        List<Space> availableTables = spaceRepository.findAvailableTablesByDate(startDate, endDate, startTime, endTime);

        assertThat(availableTables).hasSize(2);
        assertThat(availableTables.get(0).getName()).isEqualTo("Table 1");
        assertThat(availableTables.get(1).getName()).isEqualTo("Table 2");
    }

    @Test
    void testFindTablesWithReservations() {
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2024, 10, 10);

        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();

        when(spaceRepository.findTablesWithReservations(startDate, endDate))
                .thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = spaceRepository.findTablesWithReservations(startDate, endDate);

        assertThat(reservations).hasSize(2);
    }

    @Test
    void testSaveSpace() {
        Space space = new Space();
        space.setName("Meeting Room");
        space.setSpaceType("Room");
        space.setSpaceStatus("available");

        when(spaceRepository.save(space)).thenReturn(space);

        Space savedSpace = spaceRepository.save(space);

        assertThat(savedSpace).isNotNull();
        assertThat(savedSpace.getName()).isEqualTo("Meeting Room");
    }

    @Test
    void testFindSpaceById() {
        Space space = new Space();
        space.setId(1);
        space.setName("Office");

        when(spaceRepository.findById(1)).thenReturn(Optional.of(space));

        Optional<Space> foundSpace = spaceRepository.findById(1);

        assertThat(foundSpace).isPresent();
        assertThat(foundSpace.get().getName()).isEqualTo("Office");
    }
}