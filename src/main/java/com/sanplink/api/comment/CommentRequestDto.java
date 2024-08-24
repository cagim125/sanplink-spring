package com.sanplink.api.comment;

import lombok.Data;

@Data
public class CommentRequestDto {
    private Long userId;
    private String content;
}
