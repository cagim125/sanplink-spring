package com.sanplink.api.post;

import com.sanplink.api.dto.PostDto;
import com.sanplink.api.dto.PostResponsDto;
import com.sanplink.api.dto.ResponseDto;
import com.sanplink.api.user.User;
import com.sanplink.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> allPost() {
        return postRepository.findAll();
    }

    public ResponseDto<?> getPost(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        if (result.isPresent()) {
            Post post = result.get();
            PostDto postDto = new PostDto();
            postDto.setId(post.getId());
            postDto.setContent(post.getContent());
            postDto.setImageUrl(post.getImageUrl());
            postDto.setUserId(post.getUser().getId());

            System.out.println(postDto.toString());


            return ResponseDto.setSuccessData("조회 성공", postDto);
        }

        return ResponseDto.setFailed("해당 게시물이 존재하지 않습니다.");
    }

    // Save Post
    public ResponseDto<?> savePost(PostDto postDto) {
        Optional<User> user = userRepository.findById(postDto.getUserId());

        if (user.isEmpty()) {
            return ResponseDto.setFailed("해당 유저가 존재 하지 않습니다.");
        } else {
            postDto.setUserId(user.get().getId());
        }

        Post post = new Post(postDto, user.get());

        try {

            Post result = postRepository.save(post);

//            System.out.println(result.toString());

        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }


        return ResponseDto.setSuccess("게시글이 저장되었습니다.");

    }

    // Update Post
    public ResponseDto<?> updatePost(PostDto postDto) {
        try {
            Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

            post.setContent(postDto.getContent());
            post.setImageUrl(postDto.getImageUrl());

            postRepository.save(post);

        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }

        return ResponseDto.setSuccess("수정 완료 되었습니다.");
    }

    // Delete Post
    public ResponseDto<?> deletePost(Long id) {
        postRepository.deleteById(id);
        return ResponseDto.setSuccess("삭제 되었습니다.");
    }

}
