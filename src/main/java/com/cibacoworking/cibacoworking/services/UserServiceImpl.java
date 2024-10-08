package com.cibacoworking.cibacoworking.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DTOMapper;
import com.cibacoworking.cibacoworking.models.dtos.UserDTO;
import com.cibacoworking.cibacoworking.models.dtos.requests.UserRegistrationRequestDTO;
import com.cibacoworking.cibacoworking.models.entities.Role;
import com.cibacoworking.cibacoworking.models.entities.User;
import com.cibacoworking.cibacoworking.repositories.RoleRepository;
import com.cibacoworking.cibacoworking.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DTOMapper dtoMapper;

    
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

    // crear un usuario
    public UserDTO createUser(UserRegistrationRequestDTO userRegistrationDTO) throws CibaCoworkingException {
        if (isEmailAvailable(userRegistrationDTO.getEmail())) {
            if (!isPasswordFormatValid(userRegistrationDTO.getPassword())) {
                throw new CibaCoworkingException("La contrasenya ha de contenir 8 caràcters.", HttpStatus.BAD_REQUEST);
            }
            try {
                User user = dtoMapper.convertToEntity(userRegistrationDTO);
                Optional<Role> role = roleRepository.findById(2); 
                Role userRole = role.get();
                user.setRole(userRole);
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

    // Obtener usuario por su id
    public UserDTO getUserById(int id) throws CibaCoworkingException {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat l'usuari amb aquest ID", HttpStatus.NOT_FOUND);
        }
        return dtoMapper.convertToDTO(userOpt.get());
    }

    // editar usuario por id
    public UserDTO updateUser(int id, UserRegistrationRequestDTO userRegistrationDTO) throws CibaCoworkingException {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat l'usuari per actualitzar", HttpStatus.NOT_FOUND);
        }
        try {
            User existingUser = userOpt.get();
            existingUser.setName(userRegistrationDTO.getName());
            existingUser.setEmail(userRegistrationDTO.getEmail());
            existingUser.setPhone(userRegistrationDTO.getPhone());
            existingUser.setProjectName(userRegistrationDTO.getProjectName());
            if (userRegistrationDTO.getPassword() != null && !userRegistrationDTO.getPassword().isEmpty()) {

                String encryptedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
                existingUser.setPassword(encryptedPassword);
            } else {
                System.out.println("No se actualizó la contraseña ya que no se proporcionó.");
            }
            User updatedUser = userRepository.save(existingUser);
            return dtoMapper.convertToDTO(updatedUser);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut actualitzar l'usuari", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Borrar usuario por id
    public ResponseEntity<Object> deleteUser(int id) throws CibaCoworkingException {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat l'usuari per eliminar", HttpStatus.NOT_FOUND);
        }
        User existingUser = userOpt.get();
        if (existingUser.getRole().getId() == 1) {
            throw new CibaCoworkingException("No es pot eliminar l'administrador", HttpStatus.FORBIDDEN);
        }
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>("S'ha eliminat amb èxit", HttpStatus.OK);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut eliminar l'usuari", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isEmailAvailable(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.isEmpty();
    }

    private boolean isPasswordFormatValid(String password) {
        return password.length() == 8;
    }

}
