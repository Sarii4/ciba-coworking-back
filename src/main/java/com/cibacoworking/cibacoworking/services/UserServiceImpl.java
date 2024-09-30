package com.cibacoworking.cibacoworking.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
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

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
            RoleRepository roleRepository, DTOMapper dtoMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.dtoMapper = dtoMapper;

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

                // save rol como User
                Optional<Role> role = roleRepository.findById(2);
                if (role.isEmpty()) {
                    throw new CibaCoworkingException("Role not found", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                Role userRole = role.get();
                user.setRole(userRole);

                // encriptar la contraseña

                System.out.println("Password provided: " + userRegistrationDTO.getPassword());

                // Check if password is null or empty
                if (userRegistrationDTO.getPassword() == null || userRegistrationDTO.getPassword().isEmpty()) {
                    throw new CibaCoworkingException("Password is required", HttpStatus.BAD_REQUEST);
                }
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

    /*
     * public UserDTO createUser(UserRegistrationDTO userRegistrationDTO) throws
     * CibaCoworkingException {
     * 
     * if (isEmailAvailable(userRegistrationDTO.getEmail())) {
     * try {
     * User user = dtoMapper.convertToEntity(userRegistrationDTO);
     * 
     * // Hash the password before saving
     * user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
     * 
     * User savedUser = userRepository.save(user);
     * return dtoMapper.convertToDTO(savedUser);
     * } catch (Exception e) {
     * throw new CibaCoworkingException("No s'ha pogut crear l'usuari",
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * } else {
     * throw new CibaCoworkingException("Aquest email ja s'està utilitzant.",
     * HttpStatus.BAD_REQUEST);
     * }
     * }
     */

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
}
// Si al modificar no se modifica contraseña debe usar la ya existente

// borrar usuario por id
