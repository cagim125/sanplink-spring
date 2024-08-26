package com.sanplink.api.auth;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
