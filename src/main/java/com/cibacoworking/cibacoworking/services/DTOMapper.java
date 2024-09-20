package com.cibacoworking.cibacoworking.services;

import com.cibacoworking.cibacoworking.models.dtos.AdminUserDTO;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.entities.Reservation;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.ReservationRepository;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;
import com.cibacoworking.cibacoworking.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DTOMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getProjectName(),
            (user.getRole() != null) ? user.getRole().getRol() : null  
                    );
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

    public User convertToEntity(AdminUserDTO adminUserDTO) {
        User user = new User();
        user.setId(adminUserDTO.getId());
        user.setName(adminUserDTO.getName());
        user.setEmail(adminUserDTO.getEmail());
        user.setPassword(adminUserDTO.getPassword()); 
        user.setPhone(adminUserDTO.getPhone());
        user.setProjectName(adminUserDTO.getProjectName());
        return user;
    }

    public AdminUserDTO convertToAdminDTO(User user) {
        return new AdminUserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            user.getPhone(),
            user.getProjectName(),
            (user.getRole() != null) ? user.getRole().getRol() : null  
        );
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
            space.getDescription()
        );
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
            reservation.getSpace().getId(), 
            reservation.getUser().getId(), 
            reservation.getStartDate(), 
            reservation.getEndDate(), 
            reservation.getStartTime(), 
            reservation.getEndTime()
        );
    }

    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());

        Optional<Space> spaceOpt = spaceRepository.findById(reservationDTO.getSpaceId());
        if (spaceOpt.isPresent()) {
            reservation.setSpace(spaceOpt.get());
        } else {
            throw new IllegalArgumentException("Space no encontrado con id: " + reservationDTO.getSpaceId());
        }

        Optional<User> userOpt = userRepository.findById(reservationDTO.getUserId());
        if (userOpt.isPresent()) {
            reservation.setUser(userOpt.get());
        } else {
            throw new IllegalArgumentException("User no encontrado con id: " + reservationDTO.getUserId());
        }

        return reservation;
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
