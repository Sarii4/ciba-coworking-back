package com.cibacoworking.cibacoworking.repositories;


import com.cibacoworking.cibacoworking.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
