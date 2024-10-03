package com.cibacoworking.cibacoworking.models.dtos;

import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;
import com.cibacoworking.cibacoworking.repositories.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DTOMapper {

  
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getProjectName(),
                (user.getRole() != null) ? user.getRole().getRol() : null);
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setProjectName(userDTO.getProjectName());
        return user;
    }

    public UserRegistrationDTO convertToRegistrationDTO(User user) {
        return new UserRegistrationDTO(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getProjectName(),
                user.getPassword());
    }

    public User convertToEntity(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setName(userRegistrationDTO.getName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhone(userRegistrationDTO.getPhone());
        user.setProjectName(userRegistrationDTO.getProjectName());
        user.setPassword(userRegistrationDTO.getPassword());
        return user;
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public SpaceDTO convertToDTO(Space space) {
        return new SpaceDTO(
                space.getId(),
                space.getName(),
                space.getSpaceType(),
                space.getSpaceStatus(),
                space.getDescription());
    }

    public Space convertToEntity(SpaceDTO spaceDTO) {
        Space space = new Space();
        space.setId(spaceDTO.getId());
        space.setName(spaceDTO.getName());
        space.setSpaceType(spaceDTO.getSpaceType());
        space.setSpaceStatus(spaceDTO.getSpaceStatus());
        space.setDescription(spaceDTO.getDescription());
        return space;
    }

    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    public ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                convertToDTO(reservation.getUser()),
                convertToDTO(reservation.getSpace()));
    }

    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());

        Optional<Space> spaceOpt = spaceRepository.findById(reservationDTO.getSpaceDTO().getId());
        if (spaceOpt.isPresent()) {
            reservation.setSpace(spaceOpt.get());
        } else {
            throw new IllegalArgumentException("Space no encontrado con id: " + reservationDTO.getSpaceDTO().getId());
        }

        Optional<User> userOpt = userRepository.findById(reservationDTO.getUserDTO().getId());
        if (userOpt.isPresent()) {
            reservation.setUser(userOpt.get());
        } else {
            throw new IllegalArgumentException("User no encontrado con id: " + reservationDTO.getUserDTO().getId());
        }

        return reservation;
    }

}
