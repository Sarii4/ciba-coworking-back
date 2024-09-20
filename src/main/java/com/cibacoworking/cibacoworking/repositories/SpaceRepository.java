package com.cibacoworking.cibacoworking.repositories;

import com.cibacoworking.cibacoworking.models.entities.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {
}
