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

    public Post getPost(Long postId) {
        return postRepository.findById(postId).get();
    }

    public ResponseDto<?> savePost(PostDto postDto) {
        Optional<User> user = userRepository.findById(postDto.getUserId());

        if (user.isPresent()) {
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

}
