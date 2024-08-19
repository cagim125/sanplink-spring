package com.sanplink.api.user;

import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // get user
    public ResponseDto<?> getUser(String username) {
        User result = userRepository.findByUsername(username).get();
        result.setPassword("");
        return ResponseDto.setSuccessData("성공", result);
    }

    // Signup
    public ResponseDto<?> signUp(UserDto userDto) {
        String username = userDto.getUsername();

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));


        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseDto.setFailed("이미 존재하는 아이디입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        User user = new User(userDto);

        if(user.getProfileImageUrl() == ""){
            user.setProfileImageUrl("https://spring-test-bucket-123.s3.ap-northeast-2.amazonaws.com/user/anonymous.png");
        }

        try {

            User result = userRepository.save(user);

            if (result == null) {
                return ResponseDto.setSuccess("회원가입 실패");
            }

        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");

        }

        user.setPassword("");

        return ResponseDto.setSuccessData("회원가입 성공", user);
    }

}
