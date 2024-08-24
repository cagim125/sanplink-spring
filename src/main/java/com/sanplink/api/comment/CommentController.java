package com.sanplink.api.comment;

import com.sanplink.api.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{postId}/comments")
public class CommentController {
    private final CommentService commentService;



    @PostMapping
    public ResponseDto<?> saveComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto) {

        commentService.addComment(postId, requestDto.getUserId(), requestDto.getContent());

        return ResponseDto.setSuccess("댓글 저장 완료");
    }
}
