package com.sanplink.api.post;

import com.sanplink.api.config.S3Service;
import com.sanplink.api.dto.PostRequestDto;
import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final S3Service s3Service;

    @GetMapping
    public ResponseDto<?> getAllPost() {
        return postService.allPost();
    }


    @GetMapping("/presigned-url")
    @ResponseBody
    public String getUrl(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("post/" + filename);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/save")
    public ResponseDto<?> savePost(@RequestBody PostRequestDto requestDto) {
        return postService.savePost(requestDto);
    }

    @PutMapping
    public ResponseDto<?> updatePost(@RequestBody PostRequestDto postRequestDto) {
        System.out.println(postRequestDto);
        return postService.updatePost(postRequestDto);
    }

    @DeleteMapping
    public ResponseDto<?> deletePost(@RequestParam("postId") Long postId){
        System.out.println(postId);
        return postService.deletePost(postId);
    }


}
