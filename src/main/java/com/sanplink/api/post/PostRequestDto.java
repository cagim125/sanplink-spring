package com.sanplink.api.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long id;
    private String content;
    private String imageUrl;
    private Long userId;
}
