package com.sanplink.api.post;

import com.sanplink.api.dto.PostDto;
import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;


    @PostMapping("/save")
    public ResponseDto<?> savePost(@RequestBody PostDto requestDto) {
        return postService.savePost(requestDto);
    }

}
