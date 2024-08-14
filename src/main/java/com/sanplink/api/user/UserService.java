package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
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

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User registerUser(UserDto userDto) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = new User(userDto);

            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException("Already use username.");
        }
    }

}
