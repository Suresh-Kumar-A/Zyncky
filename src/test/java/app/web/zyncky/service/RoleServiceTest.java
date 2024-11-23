package app.web.zyncky.service;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.entity.Role;
import app.web.zyncky.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    final String roleName = RoleEnum.USER.name();
    private List<Role> mockedRoles;

    @BeforeEach
    void setUp() {
        mockedRoles = List.of(
                Role.builder().id(1L).name(RoleEnum.ADMIN.name()).build(),
                Role.builder().id(2L).name(RoleEnum.USER.name()).build(),
                Role.builder().id(3L).name(RoleEnum.GUEST.name()).build()
        );
    }

    @Test
    void shouldCreateNewRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(mockedRoles.get(1));
        Role createdRole = roleService.createRole(roleName);
        assertThat(createdRole).isNotNull();
    }

    @Test
    void shouldReturnAllRoles() {
        when(roleRepository.findAll()).thenReturn(mockedRoles);
        assertThat(roleService.getAllRoles()).isNotNull().hasSize(3);
    }

    @Test
    void shouldFetchSpecificRole() {
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(mockedRoles.get(1)));
        assertThat(roleService.findRoleByName(roleName)).isNotNull().isNotEmpty();
        assertThat(roleService.findRoleByName(null)).isNotNull().isEmpty();
    }
}