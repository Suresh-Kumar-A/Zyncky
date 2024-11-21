package app.web.zyncky.service;

import app.web.zyncky.model.Role;
import app.web.zyncky.repo.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    static final String USER_ROLE_NAME = "USER";
    static final String TEST_ROLE_NAME_1 = "TEST";
    static final String TEST_ROLE_NAME_2 = "";

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleService roleService;

    @Test
    public void shouldCreateNewRole() throws IllegalArgumentException {
        Role expectedRole = Role.builder().id(101).roleName(USER_ROLE_NAME).build();
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);
        Role actualRole = roleService.createRole(USER_ROLE_NAME);
        assertThat(actualRole).isNotNull().isEqualTo(expectedRole);
    }

    @Test
    void shouldNotCreateNewRole() {
        when(roleRepository.save(any(Role.class))).thenThrow(new IllegalArgumentException("Incorrect role name"));
        assertThatThrownBy(() -> roleService.createRole(TEST_ROLE_NAME_1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Incorrect role name");
        assertThatThrownBy(() -> roleService.createRole(TEST_ROLE_NAME_2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Role Name is Invalid");
        assertThatThrownBy(() -> roleService.createRole(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Role Name is Invalid");
    }

    @Test
    void shouldFindRoleByItsName() throws IllegalArgumentException {
        Role expectedRole = Role.builder().id(101).roleName(USER_ROLE_NAME).build();
        when(roleRepository.findByRoleName(USER_ROLE_NAME)).thenReturn(Optional.of(expectedRole));
        Role actualRole = roleService.findRoleByName(USER_ROLE_NAME);

        assertThat(actualRole).isNotNull().isEqualTo(expectedRole);
        verify(roleRepository, times(1)).findByRoleName(USER_ROLE_NAME);
    }

    @Test
    void shouldNotFindRoleForInvalidName() throws IllegalArgumentException {
        when(roleRepository.findByRoleName(TEST_ROLE_NAME_1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> roleService.findRoleByName(TEST_ROLE_NAME_2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Role Name is Invalid");
        assertThatThrownBy(() -> roleService.findRoleByName(TEST_ROLE_NAME_1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Role does not exist");
    }

    @Test
    void shouldGetAllTheRoles() {
        Role expectedRole = Role.builder().id(101).roleName(USER_ROLE_NAME).build();
        when(roleRepository.findAll()).thenReturn(List.of(expectedRole));
        assertThat(roleService.getAllRole()).isNotNull().hasSize(1);
    }
}