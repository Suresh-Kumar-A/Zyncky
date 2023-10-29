package app.web.zyncky.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import app.web.zyncky.constant.RoleEnum;
import app.web.zyncky.dto.UserDto;
import app.web.zyncky.exception.UserExistsException;
import app.web.zyncky.exception.UserMissingException;
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

    @Value("${app.default.admin.username}")
    private String defaultAdminUsername;

    @Value("${app.default.admin.password}")
    private String defaultAdminPassword;

    @Value("${app.default.admin.display-name}")
    private String defaultAdminDisplayName;

    public UserDto createUser(UserDto user) throws Exception {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User object is Invalid. Unable to process!");
        else if (userRepo.findByUserName(user.getUserName()).isPresent())
            throw new UserExistsException("Username (" + user.getUserName() + ") already exists!");

        User dbRecord = User.builder().userName(user.getUserName())
                .displayName(user.getDisplayName()).createdAt(new Date()).build();
        dbRecord.setRole(roleService.findRoleByName(RoleEnum.USER.name()));
        dbRecord.setUid(CommonUtils.generateToken());
        dbRecord.setPassword(customBeanUtils.encodeUsingBcryptPasswordEncoder(user.getPassword()));
        return convertToDto(userRepo.save(dbRecord));
    }

    private UserDto convertToDto(User user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User object is Invalid. Unable to process!");

        UserDto userDto = UserDto.builder().build();
        BeanUtils.copyProperties(user, userDto, "password", "role");
        userDto.setRoleName(user.getRole().getRoleName());
        return userDto;
    }

    public UserDto findByUid(String uid) throws Exception {
        if (StringUtils.hasText(uid))
            throw new IllegalArgumentException("Uid is Invalid");

        User dbUser = userRepo.findByUid(uid)
                .orElseThrow(() -> new UserMissingException("User not found!"));
        return convertToDto(dbUser);
    }

    public UserDto findByUserName(String userName) throws Exception {
        if (!StringUtils.hasText(userName))
            throw new IllegalArgumentException("Username is Invalid");

        User dbUser = userRepo.findByUserName(userName)
                .orElseThrow(() -> new UserMissingException("User not found!"));
        return convertToDto(dbUser);
    }

    public boolean doUserExists(String userName) throws Exception {
        if (!StringUtils.hasText(userName))
            throw new IllegalArgumentException("Username is Invalid");

        return userRepo.findByUserName(userName).isPresent();
    }

    public List<UserDto> getAllUser() throws Exception {
        return userRepo.findAll().stream().map(dbUser -> convertToDto(dbUser))
                .collect(Collectors.toList());
    }

    public void createDefaultAdminUser() throws Exception {
        User dbRecord = User.builder().userName(defaultAdminUsername)
                .displayName(defaultAdminDisplayName).createdAt(new Date()).build();
        dbRecord.setRole(roleService.findRoleByName(RoleEnum.ADMIN.name()));
        dbRecord.setUid(CommonUtils.generateToken());
        dbRecord.setPassword(customBeanUtils.encodeUsingBcryptPasswordEncoder(defaultAdminPassword));
        userRepo.save(dbRecord);
        System.out.println("## App Default Admin User Created...");
    }

    public void deleteUserByUsername(String userName) throws Exception {
        if (!StringUtils.hasText(userName))
            throw new IllegalArgumentException("Username is Invalid");

        Optional<User> dbEntity = userRepo.findByUserName(userName);
        if (!dbEntity.isPresent())
            throw new UserMissingException("User not found!");
        else if (dbEntity.get().getRole().getRoleName().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
            throw new IllegalArgumentException("UnAuthorized to delete an Admin User!");
        }

        userRepo.delete(dbEntity.get());
    }

    public void deleteUserByUid(String uid) throws Exception {
        if (!StringUtils.hasText(uid))
            throw new IllegalArgumentException("Uid is Invalid");

        Optional<User> dbEntity = userRepo.findByUid(uid);
        if (!dbEntity.isPresent())
            throw new UserMissingException("User not found!");
        else if (dbEntity.get().getRole().getRoleName().equalsIgnoreCase(RoleEnum.ADMIN.name())) {
            throw new IllegalArgumentException("UnAuthorized to delete an Admin User!");
        }

        userRepo.delete(dbEntity.get());
    }

    public UserDto updateUser(UserDto userDto) throws Exception {
        if (Objects.isNull(userDto))
            throw new IllegalArgumentException("User object is Invalid. Unable to process!!");
        else if (!StringUtils.hasText(userDto.getUserName()))
            throw new IllegalArgumentException("Username is Invalid");

        User dbUser = userRepo.findByUserName(userDto.getUserName())
                .orElseThrow(() -> new UserMissingException("User not found!"));

        dbUser.setDisplayName(userDto.getDisplayName());
        dbUser.setPassword(customBeanUtils.encodeUsingBcryptPasswordEncoder(userDto.getPassword()));
        return convertToDto(userRepo.save(dbUser));

    }
}
