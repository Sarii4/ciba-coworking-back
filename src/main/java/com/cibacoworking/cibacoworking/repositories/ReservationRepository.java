package com.cibacoworking.cibacoworking.repositories;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByUserId(int userId);
     @Query("SELECT r FROM Reservation r WHERE r.space.id = :spaceId AND r.startDate >= :startDate AND r.endDate <= :endDate")
    List<Reservation> findAvailableSpacesByIdAndDate(
            @Param("spaceId") int spaceId, 
            @Param("startDate") LocalDate startDate, 
            @Param("endDate") LocalDate endDate
    );
}
