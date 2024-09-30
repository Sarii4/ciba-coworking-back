package com.cibacoworking.cibacoworking.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.ReservationDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.UserRegistrationDTO;
import com.cibacoworking.cibacoworking.models.entities.Role;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.RoleRepository;
import com.cibacoworking.cibacoworking.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DTOMapper dtoMapper;
    private final ReservationService reservationService;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
            RoleRepository roleRepository, DTOMapper dtoMapper, ReservationService reservationService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.dtoMapper = dtoMapper;
        this.reservationService = reservationService;

        System.out.println("PasswordEncoder injected: " + (passwordEncoder != null));
    }

    // Obtener todos usuarios para admin dashboard
    public List<UserDTO> getAllUsers() throws CibaCoworkingException {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new CibaCoworkingException("No s'han trobat cap usuari", HttpStatus.NOT_FOUND);
        }

        return users.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // crear un usuario(comprobar existencia mail)
    public UserDTO createUser(UserRegistrationDTO userRegistrationDTO) throws CibaCoworkingException {

        if (isEmailAvailable(userRegistrationDTO.getEmail())) {
            try {
                User user = dtoMapper.convertToEntity(userRegistrationDTO);

                // guardar rol como User
                Optional<Role> role = roleRepository.findById(2);
                Role userRole = role.get();
                user.setRole(userRole);

                // encriptar la contraseña
                user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

                User savedUser = userRepository.save(user);
                return dtoMapper.convertToDTO(savedUser);
            } catch (Exception e) {
                throw new CibaCoworkingException("No s'ha pogut crear l'usuari", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new CibaCoworkingException("Aquest email ja s'està utilitzant.", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isEmailAvailable(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.isEmpty();
    }


    // Obtener usuario por su id
    public UserDTO getUserById(int id) throws CibaCoworkingException {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat l'usuari amb aquest ID", HttpStatus.NOT_FOUND);
        }

        return dtoMapper.convertToDTO(userOpt.get());
    }

    // editar usuario por id
    public UserDTO updateUser(int id, UserDTO userDTO) throws CibaCoworkingException {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat l'usuari per actualitzar", HttpStatus.NOT_FOUND);
        }

        try {

            User existingUser = userOpt.get();
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPhone(userDTO.getPhone());
            existingUser.setProjectName(userDTO.getProjectName());

            User updatedUser = userRepository.save(existingUser);
            return dtoMapper.convertToDTO(updatedUser);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut actualitzar l'usuari", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
// Si al modificar no se modifica contraseña debe usar la ya existente

// borrar usuario por id
public void deleteUser(int id) throws CibaCoworkingException {
    Optional<User> userOpt = userRepository.findById(id);

    if (!userOpt.isPresent()) {
        throw new CibaCoworkingException("No s'ha trobat l'usuari per eliminar", HttpStatus.NOT_FOUND);
    }

    // Obtener las reservas activas del usuario 
    List<ReservationDTO> userReservations = reservationService.getReservationsByUser(id);

    try {
        userRepository.deleteById(id);
    } catch (Exception e) {
        throw new CibaCoworkingException("No s'ha pogut eliminar l'usuari", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}

