package com.sanplink.api.user;

import com.sanplink.api.dto.LoginDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signUp")
    public ResponseDto<?> signUp(@RequestBody SignUpDto signUpDto) {
        System.out.println(signUpDto.toString());

        ResponseDto<?> result = authService.sighUp(signUpDto);

        return result;
    }

    @PostMapping("/login")
    public ResponseDto<?> login(@RequestBody LoginDto dto) {
        ResponseDto<?> result = authService.login(dto);

        return result;
    }

//    @PostMapping("/login/jwt")
//    @ResponseBody
//    public String loginJwt(@RequestBody Map<String, String> data) {
//
////        System.out.println(data.get("username"));
////        System.out.println(data.get("password"));
//
//        var authToken = new UsernamePasswordAuthenticationToken(
//                data.get("username"), data.get("password")
//        );
//
//        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//
//        return "";
//    }
}
