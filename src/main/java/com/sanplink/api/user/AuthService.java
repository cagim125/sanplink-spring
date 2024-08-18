package com.sanplink.api.user;

import com.sanplink.api.dto.LoginDto;
import com.sanplink.api.dto.LoginResponseDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public ResponseDto<?> sighUp(SignUpDto dto) {
        String username = dto.getUsername();
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // username 중복 확인
        try {
            if(userRepository.existsByUsername(username)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        User user = new User(dto);

        user.setPassword(encodedPassword);

        // DB에 Entity 저장
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        return ResponseDto.setSuccess("회원 생성 성공");
    }

    public ResponseDto<?> login(LoginDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        User user;

        try{
            user = userRepository.findByUsername(username).orElse(null);
            if(user == null) {
                return ResponseDto.setFailed("아이디로 등록된 계정이 존재 하지 않음");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        String encodedPassword = user.getPassword();

        if (!passwordEncoder.matches(password, encodedPassword )) {
            return ResponseDto.setFailed("아이디나 비번이 일치하지 않습니다.");
        }

        user.setPassword("");
        int exprTime = 3600;
//        String token = "";
        String token = tokenProvider.createJwt(user.getUsername(), exprTime);

        if (token == null) {
            return ResponseDto.setFailed("토큰 생성 실패");
        }


        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, user);

        return ResponseDto.setSuccessData("로그인 성공", loginResponseDto);
    }
}
