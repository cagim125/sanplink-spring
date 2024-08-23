package com.sanplink.api.user;

import com.sanplink.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Mypage
    public ResponseDto<?> myPage(Long userId) {
        User result = userRepository.findById(userId).orElseThrow();
        MyPageResponseDto userDto = new MyPageResponseDto();
        userDto.setId(result.getId());
        userDto.setUsername(result.getUsername());
        userDto.setProfileImgUrl(result.getProfileImageUrl());

        List<PostResponseDto> postRequestDtos = result.getPosts().stream().map(post -> {
            PostResponseDto postResponseDto = new PostResponseDto();
            postResponseDto.setId(post.getId());
            postResponseDto.setContent(post.getContent());
            postResponseDto.setImageUrl(post.getImageUrl());

            return postResponseDto;
        }).collect(Collectors.toUnmodifiableList());

        userDto.setPosts(postRequestDtos);

        return ResponseDto.setSuccessData("성공", userDto);
    }

    // Signup
    public ResponseDto<?> signUp(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();

        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));


        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseDto.setFailed("이미 존재하는 아이디입니다.");
            }
        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        User user = new User(userRequestDto);

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
