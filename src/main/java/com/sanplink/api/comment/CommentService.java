package com.sanplink.api.comment;

import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.post.Post;
import com.sanplink.api.post.PostRepository;
import com.sanplink.api.user.User;
import com.sanplink.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public void addComment(Long postId, Long userId, String content) {
        try{
            // 게시글과 사용자 찾기
            Post post = postRepository.findById(postId).orElseThrow(
                    ()-> new RuntimeException("Post not found"));
            User user = userRepository.findById(userId).orElseThrow(() ->
                    new RuntimeException("User not found"));

            // 댓글 저장
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            comment.setContent(content);

            commentRepository.save(comment);



        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

}
