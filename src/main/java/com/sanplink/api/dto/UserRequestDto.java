package com.sanplink.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRequestDto {

    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
}
