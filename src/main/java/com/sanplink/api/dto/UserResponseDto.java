package com.sanplink.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String profileImgUrl;
    private List<PostDto> posts;
}
