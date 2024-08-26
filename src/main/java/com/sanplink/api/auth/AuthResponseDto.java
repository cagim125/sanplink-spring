package com.sanplink.api.auth;

import com.sanplink.api.user.User;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private Long userId;
    private String username;
    private String email;


    public AuthResponseDto(String token,Long userId, String username, String email) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}