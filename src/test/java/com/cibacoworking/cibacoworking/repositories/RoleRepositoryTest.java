package com.cibacoworking.cibacoworking.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.cibacoworking.cibacoworking.models.entities.Role;

@SpringBootTest
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleRepositoryTest roleRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRole() {

        Role role = new Role();
        role.setRol("Admin");

        when(roleRepository.save(role)).thenReturn(role);

        Role savedRole = roleRepository.save(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getRol()).isEqualTo("Admin");
    }

    @Test
    void testFindRoleById() {

        Role role = new Role();
        role.setId(1);
        role.setRol("Admin");

        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        Optional<Role> foundRole = roleRepository.findById(1);

        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRol()).isEqualTo("Admin");
    }

    @Test
    void testFindAllRoles() {

        Role role1 = new Role();
        role1.setRol("Admin");

        Role role2 = new Role();
        role2.setRol("User");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> roles = roleRepository.findAll();

        assertThat(roles).hasSize(2);
        assertThat(roles.get(0).getRol()).isEqualTo("Admin");
        assertThat(roles.get(1).getRol()).isEqualTo("User");
    }

    @Test
    void testDeleteRoleById() {

        Role role = new Role();
        role.setId(1);
        role.setRol("Admin");

        doNothing().when(roleRepository).deleteById(1);

        roleRepository.deleteById(1);

        verify(roleRepository, times(1)).deleteById(1);
    }
}