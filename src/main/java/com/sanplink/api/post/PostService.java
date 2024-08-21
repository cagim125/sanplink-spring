package com.sanplink.api.post;

import com.sanplink.api.dto.PostDto;
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

    public ResponseDto<Post> getPost(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        if (result.isPresent()) {
            return ResponseDto.setSuccessData("조회 성공", result.get());
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
            if(!postRepository.existsById(postDto.getId())) {
                return ResponseDto.setFailed("게시물이 존재하지 않음");
            }

            Post post = new Post(postDto);

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
