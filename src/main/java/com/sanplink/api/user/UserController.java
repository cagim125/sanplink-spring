package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUser() {
        List<User> users = userService.getUser();

        return users;
    }

    @PostMapping("/register")
    public User registredUser(@RequestBody UserDto userDto) {
        return userService.registerUser(userDto);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 상태 코드 반환
    public ResponseEntity<String> handleUserAlreadyExistsException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
