package app.web.zyncky.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.web.zyncky.dto.UserDto;
import app.web.zyncky.model.User;
import app.web.zyncky.repo.UserRepository;
import app.web.zyncky.util.CommonUtils;
import app.web.zyncky.util.CustomBeanUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    private final RoleService roleService;

    private final CustomBeanUtils customBeanUtils;

    public UserDto createUser(UserDto user) throws Exception {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User object is Invalid");

        User dbRecord = User.builder().build();
        BeanUtils.copyProperties(user, dbRecord, "roleName", "id", "uid");
        dbRecord.setRole(roleService.findRoleByName(user.getRoleName()));
        dbRecord.setUid(CommonUtils.generateToken());
        dbRecord.setPassword(customBeanUtils.encodeUsingBcryptPasswordEncoder(user.getPassword()));
        return convertToDto(userRepo.save(dbRecord));
    }

    private UserDto convertToDto(User user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User object is Invalid");

        UserDto userDto = UserDto.builder().build();
        BeanUtils.copyProperties(user, userDto, "password", "role");
        userDto.setRoleName(user.getRole().getRoleName());
        return userDto;
    }

    public UserDto findByUid(String uid) throws Exception {
        if (StringUtils.hasText(uid))
            throw new IllegalArgumentException("Uid is Invalid");

        User dbUser = userRepo.findByUid(uid).orElseThrow(() -> new Exception("User not found!"));
        return convertToDto(dbUser);
    }

    public UserDto findByUserName(String userName) throws Exception {
        if (StringUtils.hasText(userName))
            throw new IllegalArgumentException("Uid is Invalid");

        User dbUser = userRepo.findByUserName(userName)
                .orElseThrow(() -> new Exception("User not found!"));
        return convertToDto(dbUser);
    }

    public List<UserDto> getAllUser() throws Exception {
        return userRepo.findAll().stream().map(dbUser -> convertToDto(dbUser))
                .collect(Collectors.toList());
    }
}
