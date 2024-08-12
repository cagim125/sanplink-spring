package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUser() {
        List<User> users = userService.getUser();
        users.forEach((user) -> {
            System.out.println("user : " + user);
        });


        return "user API 호출";
    }

}
