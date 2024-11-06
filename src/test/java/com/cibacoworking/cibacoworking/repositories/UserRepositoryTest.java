package com.cibacoworking.cibacoworking.repositories;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cibacoworking.cibacoworking.models.entities.Role;
import com.cibacoworking.cibacoworking.models.entities.User;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRepositoryTest userRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByEmail() {

        User user = new User();
        user.setId(1);
        user.setName("Manolo Díaz");
        user.setEmail("manolodiaz@gmail.com");
        user.setPassword("password");
        user.setPhone("123456789");
        user.setProjectName("La Ciba Coworking");
    

        Role role = new Role();
        role.setId(2);
        role.setRol("User");
        user.setRole(role);
    
        when(userRepository.findByEmail("manolodiaz@gmail.com")).thenReturn(Optional.of(user));
    
        Optional<User> foundUser = userRepository.findByEmail("manolodiaz@gmail.com");
    
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("manolodiaz@gmail.com");
        assertThat(foundUser.get().getName()).isEqualTo("Manolo Díaz");
        assertThat(foundUser.get().getPhone()).isEqualTo("123456789");
        assertThat(foundUser.get().getProjectName()).isEqualTo("La Ciba Coworking");
        assertThat(foundUser.get().getRole().getRol()).isEqualTo("User");
    }

    @Test
    void testSaveUser() {

        User user = new User();
        user.setEmail("newuser@example.com");
        user.setPassword("newpassword");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("newuser@example.com");
    }

    @Test
    void testDeleteUser() {

        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");

        userRepository.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}