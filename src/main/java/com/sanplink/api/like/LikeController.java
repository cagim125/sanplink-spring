package com.sanplink.api.like;

import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseDto<?> toggleLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        boolean liked = likeService.toggleLike(postId, userId);

        if(liked) {
            return ResponseDto.setSuccess("Post liked");
        } else {
            return ResponseDto.setSuccess("Post unliked");
        }
    }


}
