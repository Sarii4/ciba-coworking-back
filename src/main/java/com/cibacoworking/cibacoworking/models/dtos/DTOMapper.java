package com.cibacoworking.cibacoworking.models.dtos;

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

    
    //Convertimos User a DTO
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

    //Convertimos userDTO a User
    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setProjectName(userDTO.getProjectName());
        return user;
    }
   
    //Convertimos User a userRegistrationDTO
    public UserRegistrationDTO convertToRegistrationDTO(User user) {
        return new UserRegistrationDTO(
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getProjectName(),
            user.getPassword());
    }

    //Convertimos userRegistrationDTO a User
    public User convertToEntity(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setName(userRegistrationDTO.getName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setPhone(userRegistrationDTO.getPhone());
        user.setProjectName(userRegistrationDTO.getProjectName());
        user.setPassword(userRegistrationDTO.getPassword());
        return user;
    }

    //Convertimos AdminUserDTO a User
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

    //Convertimos User a AdminUserDTO
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

    //Obtenemos user po Id
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    //Guardamos User
    public User save(User user) {
        return userRepository.save(user);
    }

    
    //Convertimos Space a SpaceDTO
    public SpaceDTO convertToDTO(Space space) {
        return new SpaceDTO(
            space.getId(), 
            space.getName(), 
            space.getSpaceType(), 
            space.getSpaceStatus(), 
            space.getDescription()
        );
    }

    //Convertimos SpaceDTO a Space
    public Space convertToEntity(SpaceDTO spaceDTO) {
        Space space = new Space();
        space.setId(spaceDTO.getId());
        space.setName(spaceDTO.getName());
        space.setSpaceType(spaceDTO.getSpaceType());
        space.setSpaceStatus(spaceDTO.getSpaceStatus());
        space.setDescription(spaceDTO.getDescription());
        return space;
    }

    //Guardamos Space
    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    //Convertimos Reservation a ReservationDTO
    public ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO (
            reservation.getId(), 
            reservation.getStartDate(), 
            reservation.getEndDate(), 
            reservation.getStartTime(), 
            reservation.getEndTime(),
            convertToDTO(reservation.getUser()),
            convertToDTO(reservation.getSpace())
        );
    }

    //Convertimos ReservationDTO a Reservation
    public Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        //reservation.setId(reservationDTO.getId());
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

    //Guardamos reserva
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
