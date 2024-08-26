package com.sanplink.api.auth;

import com.sanplink.api.dto.LoginDto;
import com.sanplink.api.dto.LoginResponseDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.dto.SignUpDto;
import com.sanplink.api.security.JwtUtil;
import com.sanplink.api.user.User;
import com.sanplink.api.user.UserRepository;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
//    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

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

    public ResponseDto<?> login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        String validationPassword = passwordEncoder.encode(password);

        Optional<User> user;

        try{
            user = userRepository.findByUsername(username);
            if(user.isEmpty()) {
                return ResponseDto.setFailed("아이디로 등록된 계정이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        String encodedPassword = user.get().getPassword();

        if (!passwordEncoder.matches(password, encodedPassword )) {
            return ResponseDto.setFailed("아이디나 비번이 일치하지 않습니다.");
        }

        user.get().setPassword("");

        String token = jwtUtil.generateToken(username);

        if (token == null) {
            return ResponseDto.setFailed("토큰 생성 실패");
        }

        AuthResponseDto authResponseDto = new AuthResponseDto(
                token,
                user.get().getUsername(),
                user.get().getEmail()
                );

        return ResponseDto.setSuccessData("로그인 성공", authResponseDto);
    }
}
