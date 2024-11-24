package app.web.zyncky.service;

import app.web.zyncky.domain.UserDto;
import app.web.zyncky.entity.User;
import app.web.zyncky.repository.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class UserService {

    final RoleService roleService;
    final UserRepository userRepository;
    final SecurityService securityService;

    public Optional<UserDto> createUser(UserDto userDto) {
        userDto.setPassword(securityService.encryptText(userDto.getPassword()));
        return Optional.ofNullable(
                mapToUserDto(
                        userRepository.save(
                                mapToNewUser(userDto)
                        )
                )
        );
    }


    public UserDto mapToUserDto(@Nonnull User user) {
        return UserDto.builder()
                .uid(user.getUid().toString())
                .displayName(user.getDisplayName())
                .emailAddress(user.getEmailAddress())
                .createdAt(user.getCreatedAt())
                .roleName(user.getRole().getName())
                .build();
    }

    public User mapToNewUser(UserDto userDto) {
        return User.builder().role(roleService.findRoleByName(userDto.getRoleName()).orElseThrow())
                .emailAddress(userDto.getEmailAddress())
                .displayName(userDto.getDisplayName()).password(userDto.getPassword())
                .createdAt(userDto.getCreatedAt()).build();
    }

    public User mapToExistingUser(UserDto mockedUserDto) {
        User existingUser = userRepository.findById(UUID.fromString(mockedUserDto.getUid())).orElseThrow();
        existingUser.setDisplayName(mockedUserDto.getDisplayName());
        return existingUser;
    }
}
