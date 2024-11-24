package app.web.zyncky.service;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.domain.UserDto;
import app.web.zyncky.entity.Role;
import app.web.zyncky.entity.User;
import app.web.zyncky.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private SecurityService securityService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserService userService;

    private User mockedUser;
    private Role mockedUserRole;
    private UserDto mockedUserDto;
    private UserDto mockedUserDtoWithUUID;

    @BeforeEach
    void setUp() {
        mockedUserRole = Role.builder().id(2L).name(RoleEnum.USER.name()).build();
        mockedUser = User.builder().role(mockedUserRole).uid(UUID.randomUUID())
                .createdAt(Timestamp.from(Instant.now()))
                .emailAddress("mockeduser@test.com").password("$#safd!234")
                .displayName("Mocked User").build();
        mockedUserDto = UserDto.builder().createdAt(Timestamp.from(Instant.now()))
                .emailAddress("mockeduser@test.com").password("$#safd!234")
                .displayName("Mocked User").roleName(RoleEnum.USER.name()).build();
        mockedUserDtoWithUUID = UserDto.builder().uid(UUID.randomUUID().toString())
                .createdAt(Timestamp.from(Instant.now()))
                .emailAddress("mockeduser@test.com").password("$#safd!234")
                .displayName("Mocked User").roleName(RoleEnum.USER.name()).build();
    }

    @Test
    void shouldCreateUser() {
        when(securityService.encryptText(anyString())).thenReturn("$#sa@%^&");
        when(roleService.findRoleByName(RoleEnum.USER.name())).thenReturn(Optional.of(mockedUserRole));
        when(userRepository.save(any(User.class))).thenReturn(mockedUser);
        assertThat(userService.createUser(mockedUserDto)).isNotEmpty();

    }

    @Test
    void shouldMapFromUserDtoToNewUser() {
        when(roleService.findRoleByName(RoleEnum.USER.name())).thenReturn(Optional.of(mockedUserRole));
        assertThat(userService.mapToNewUser(mockedUserDto)).isNotNull()
                .satisfies(user->{
                    assertThat(user.getDisplayName()).isNotBlank();
                    assertThat(user.getEmailAddress()).isNotBlank();
                    assertThat(user.getRole().getName()).isEqualTo(mockedUserDto.getRoleName());
                });
    }

    @Test
    void shouldMapFromUserDtoToExistingUser() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockedUser));
        assertThat(userService.mapToExistingUser(mockedUserDtoWithUUID)).isNotNull()
                .satisfies(user->{
                    assertThat(user.getUid()).isNotNull();
                    assertThat(user.getDisplayName()).isNotBlank();
                    assertThat(user.getEmailAddress()).isNotBlank();
                    assertThat(user.getRole().getName()).isEqualTo(mockedUserDtoWithUUID.getRoleName());
                });
    }

    @Test
    void shouldMapFromUserToUserDto() {
        assertThat(userService.mapToUserDto(mockedUser)).isNotNull()
                .satisfies(user->{
                    assertThat(user.getUid()).isNotBlank();
                    assertThat(user.getDisplayName()).isNotBlank();
                    assertThat(user.getEmailAddress()).isNotBlank();
                    assertThat(user.getRoleName()).isEqualTo(mockedUser.getRole().getName());
                });
    }

}