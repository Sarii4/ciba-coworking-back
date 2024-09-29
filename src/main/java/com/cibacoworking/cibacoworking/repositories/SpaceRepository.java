package com.cibacoworking.cibacoworking.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {

//Esta query trae el estado de la mesa concreta (actiu/inactiu)
@Query("SELECT s.spaceStatus FROM Space s WHERE s.id = :spaceId")
    String findSpaceStatusById(@Param("spaceId") int spaceId);


//Esta query trae todas las mesas que estan en "actiu" y no tiene reservas para este periodo
    @Query("SELECT s FROM Space s " +
    "WHERE s.id BETWEEN 4 AND 53 " +
    "AND s.spaceStatus = 'actiu' " +  
    "AND NOT EXISTS ( " +  
    "    SELECT r FROM Reservation r " +
    "    WHERE r.space.id = s.id " +
    "    AND ( " +
    "        (r.startDate BETWEEN :startDate AND :endDate) " +
    "        OR (r.endDate BETWEEN :startDate AND :endDate) " +
    "        OR (:startDate BETWEEN r.startDate AND r.endDate AND :endDate BETWEEN r.startDate AND r.endDate) " +
    "        OR (r.startTime < :endTime AND r.endTime > :startTime) " +
    "    ) " +
    ")")
    List<Space> findAvailableTablesByDate (
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate,
        @Param("startTime") LocalTime startTime,
        @Param("endTime") LocalTime endTime
    );

    @Query("SELECT r FROM Space s " +
    "LEFT JOIN Reservation r ON r.space.id = s.id " +
    "WHERE s.id BETWEEN 4 AND 53 " +
    "AND s.spaceStatus = 'inactiu' " +
    "AND EXISTS ( " +  
    "    SELECT r FROM Reservation r " +
    "    WHERE r.space.id = s.id " +
    "    AND ( " +
    "        (r.startDate BETWEEN :startDate AND :endDate) " +
    "        OR (r.endDate BETWEEN :startDate AND :endDate) " +
    "        OR (:startDate BETWEEN r.startDate AND r.endDate AND :endDate BETWEEN r.startDate AND r.endDate) " +
    "    ) " +
    ")")
    List<Reservation> findTablesWithReservations(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
}
