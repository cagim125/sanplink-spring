package com.sanplink.api.user;

import com.sanplink.api.config.S3Service;
import com.sanplink.api.dto.ResponseDto;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3Service s3Service;

    private final AuthenticationManager authenticationManager;

    @GetMapping("/check")
    public String check() {
        return "Suceess";
    }


    @GetMapping("/presigned-url")
    @ResponseBody
    public String getUrl(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("user/" + filename);
        return result;
    }

    @PostMapping("/signUp")
    @ResponseBody
    public ResponseDto<?> signUp(@RequestBody UserDto userDto) {
//        System.out.println(userDto.toString());
        return userService.signUp(userDto);
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


    @GetMapping("/my-page/{username}")
    public ResponseDto<?> myPage(@PathVariable("username") String username) {
        return userService.getUser(username);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409 상태 코드 반환
    public ResponseEntity<String> handleUserAlreadyExistsException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
