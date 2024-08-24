package com.sanplink.api.comment;

import com.sanplink.api.post.PostResponseDto;
import com.sanplink.api.dto.UserResponseDto;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private PostResponseDto post;
    private UserResponseDto user;
}
