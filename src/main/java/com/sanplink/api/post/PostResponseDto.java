package com.sanplink.api.post;

import com.sanplink.api.comment.CommentDto;
import com.sanplink.api.dto.UserResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private String imageUrl;
    private UserResponseDto user;
    private List<CommentDto> comments;

}
