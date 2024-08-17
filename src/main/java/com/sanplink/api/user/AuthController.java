package com.sanplink.api.user;

import com.sanplink.api.dto.LoginDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
}
