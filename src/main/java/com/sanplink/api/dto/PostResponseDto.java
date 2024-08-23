package com.sanplink.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String content;
    private String imageUrl;
    private UserResponseDto user;
}
