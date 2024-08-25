package com.sanplink.api.like;

import com.sanplink.api.post.Post;
import com.sanplink.api.post.PostRepository;
import com.sanplink.api.user.User;
import com.sanplink.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LikesRepository likesRepository;

    public boolean toggleLike(Long postId, Long userId) {
        //게시글 및 사용자 찾기
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // 이미 좋아요를 눌렀는지 확인
        Optional<Likes> existingLike = likesRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            // 이미 좋아요가 눌러 있으면 취소
            likesRepository.delete(existingLike.get());
            return false;
        } else {
            Likes likes = new Likes();
            likes.setPost(post);
            likes.setUser(user);
            likesRepository.save(likes);
            return true;
        }
    }

}
