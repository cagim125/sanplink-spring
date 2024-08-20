package com.sanplink.api.post;

import com.sanplink.api.config.S3Service;
import com.sanplink.api.dto.PostDto;
import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final S3Service s3Service;


    @GetMapping("/presigned-url")
    @ResponseBody
    public String getUrl(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("post/" + filename);
        return result;
    }

    @PostMapping("/save")
    public ResponseDto<?> savePost(@RequestBody PostDto requestDto) {
        return postService.savePost(requestDto);
    }

}
