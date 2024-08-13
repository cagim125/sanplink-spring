package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
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

}
