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
        user.setEmail("user@example.com");
        user.setPassword("password");


        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));


        Optional<User> foundUser = userRepository.findByEmail("user@example.com");


        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("user@example.com");
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