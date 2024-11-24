package app.web.zyncky.controller;

import app.web.zyncky.domain.UserDto;
import app.web.zyncky.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        Optional<UserDto> optionalUser = userService.createUser(userDto);
        return optionalUser.map(dto -> ResponseEntity.created(
                URI.create("/api/users/%s".formatted(userDto.getUid())
                )).body(dto)).orElseGet(() -> ResponseEntity.badRequest().body(userDto));

    }
}
