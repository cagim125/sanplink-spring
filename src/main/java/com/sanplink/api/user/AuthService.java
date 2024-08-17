package com.sanplink.api.user;

import com.sanplink.api.dto.LoginDto;
import com.sanplink.api.dto.LoginResponseDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public ResponseDto<?> sighUp(SignUpDto dto) {
        String username = dto.getUsername();

        // username 중복 확인
        try {
            if(userRepository.existsByUsername(username)) {
                return ResponseDto.setFailed("중복된 Email 입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        User user = new User(dto);

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

        try{
            boolean existed = userRepository.existsByUsernameAndPassword(username, password);
            if(!existed) {
                return ResponseDto.setFailed("아이디나 비밀번호가 맞지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        User user = null;

        try {
            user = userRepository.findByUsername(username).get();
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        user.setPassword("");
        int exprTime = 3600000;
        String token = "";

        LoginResponseDto loginResponseDto = new LoginResponseDto(token, exprTime, user);

        return ResponseDto.setSuccessData("로그인 성공", loginResponseDto);
    }
}
