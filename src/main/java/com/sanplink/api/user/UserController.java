package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @GetMapping("check")
    public String check() {
        return "Suceess";
    }

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
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {

        try{
            // UsernamePasswordAuthenticationToken을 사용해 사용자를 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword())
            );
            // 인증 성공 시
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // username, password, authority 값을 저장할 객체
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();



            return ResponseEntity.ok(userDetails);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }


    @GetMapping("/my-page")
    public void myPage(Authentication auth) {

        System.out.println(auth);
//        System.out.println(auth.getName());
        System.out.println("isAuth : " + auth.isAuthenticated());
//        System.out.println(auth.getAuthorities());

//        return auth;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 상태 코드 반환
    public ResponseEntity<String> handleUserAlreadyExistsException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
