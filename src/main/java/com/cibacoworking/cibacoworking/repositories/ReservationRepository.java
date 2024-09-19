package com.cibacoworking.cibacoworking.repositories;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
