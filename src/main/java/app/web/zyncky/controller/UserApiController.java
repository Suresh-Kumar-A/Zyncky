package app.web.zyncky.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.web.zyncky.dto.UserDto;
import app.web.zyncky.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public final class UserApiController {

    private final UserService userService;

    @GetMapping({ "/", "" })
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> listAllUsers() throws Exception {
        return userService.getAllUser();
    }

    @GetMapping("/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto findByUserName(@PathVariable String userName) throws Exception {
        return userService.findByUserName(userName);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody UserDto userDto) throws Exception {
        return userService.createUser(userDto);
    }

    @PatchMapping("/{userName}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{userName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByUserName(@PathVariable String userName) throws Exception {
        userService.deleteUserByUsername(userName);
    }
}
