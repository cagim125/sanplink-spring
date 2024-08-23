package com.sanplink.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String profileImageUrl;
}
