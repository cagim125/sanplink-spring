package com.sanplink.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public User registerUser(UserDto userDto) {
        User user = new User(userDto);

        return userRepository.save(user);
    }

}
