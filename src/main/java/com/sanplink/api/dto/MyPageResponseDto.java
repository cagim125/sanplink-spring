package com.sanplink.api.dto;

import com.sanplink.api.post.PostResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MyPageResponseDto {
    private Long id;
    private String username;
    private String profileImgUrl;
    private List<PostResponseDto> posts;
}
