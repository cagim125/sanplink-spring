package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username ,
            @RequestParam String password
    ) {
        if (username != null && password != null) {
            return ResponseEntity.ok("데이터 잘 넘어옴");
        }

        return ResponseEntity.ok("데이터 잘 안 넘어옴");
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {

        if (auth == null) {
            return "login";
        }
        System.out.println(auth);
//        System.out.println(auth.getName());
        System.out.println("isAuth : " + auth.isAuthenticated());
//        System.out.println(auth.getAuthorities());

        return auth.getName();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 상태 코드 반환
    public ResponseEntity<String> handleUserAlreadyExistsException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
