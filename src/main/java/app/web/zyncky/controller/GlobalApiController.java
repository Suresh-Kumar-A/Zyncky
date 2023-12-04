package app.web.zyncky.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.web.zyncky.dto.UserDto;
import app.web.zyncky.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GlobalApiController {

    @Value("${app.version}")
    private String appVersion;

    private final UserService userService;

    @GetMapping("/version")
    @ResponseStatus(value = HttpStatus.OK)
    public String appVersion() {
        return appVersion;
    }

    @PostMapping("/create-account")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody UserDto userDto) throws Exception {
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, Object> authenticateUserAndGenerateJwtToken(@RequestBody UserDto userDto) throws Exception {
        return userService.authenticateUserAndGenerateJwtToken(userDto);
    }

}
