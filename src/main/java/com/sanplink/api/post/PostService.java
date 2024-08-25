package com.sanplink.api.post;

import com.sanplink.api.comment.CommentDto;
import com.sanplink.api.dto.*;
import com.sanplink.api.like.Likes;
import com.sanplink.api.user.User;
import com.sanplink.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResponseDto<?> allPost() {
        try {
            List<Post> posts = postRepository.findAll();

            // Post 엔티티들을 PostResponseDto로 변환
            List<PostResponseDto> postRequestDtos = posts.stream().map(post-> {
                PostResponseDto postResponseDto = new PostResponseDto();
                postResponseDto.setId(post.getId());
                postResponseDto.setContent(post.getContent());
                postResponseDto.setImageUrl(post.getImageUrl());

                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setId(post.getUser().getId());
                userResponseDto.setUsername(post.getUser().getUsername());
                userResponseDto.setProfileImageUrl(post.getUser().getProfileImageUrl());

                List<CommentDto> commentDtos =  post.getComments().stream().map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setContent(comment.getContent());
                    commentDto.setUserName(comment.getUser().getUsername());

                    return commentDto;
                }).collect(Collectors.toUnmodifiableList());




                postResponseDto.setUser(userResponseDto);
                postResponseDto.setComments(commentDtos);

                return postResponseDto;
            }).collect(Collectors.toUnmodifiableList());

            return ResponseDto.setSuccessData("조회 성공", postRequestDtos);


        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }
    }

    // 게시물
    public ResponseDto<?> getPost(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        if (result.isPresent()) {
            Post post = result.get();
            PostRequestDto postRequestDto = new PostRequestDto();
            postRequestDto.setId(post.getId());
            postRequestDto.setContent(post.getContent());
            postRequestDto.setImageUrl(post.getImageUrl());
            postRequestDto.setUserId(post.getUser().getId());

//            System.out.println(postDto.toString());


            return ResponseDto.setSuccessData("조회 성공", postRequestDto);
        }

        return ResponseDto.setFailed("해당 게시물이 존재하지 않습니다.");
    }

    // Save Post
    public ResponseDto<?> savePost(PostRequestDto postRequestDto) {
        Optional<User> user = userRepository.findById(postRequestDto.getUserId());

        if (user.isEmpty()) {
            return ResponseDto.setFailed("해당 유저가 존재 하지 않습니다.");
        } else {
            postRequestDto.setUserId(user.get().getId());
        }

        Post post = new Post(postRequestDto, user.get());

        try {

            Post result = postRepository.save(post);

//            System.out.println(result.toString());

        } catch (Exception e) {
            return ResponseDto.setFailed("DB 연결 실패");
        }


        return ResponseDto.setSuccess("게시글이 저장되었습니다.");

    }

    // Update Post
    public ResponseDto<?> updatePost(PostRequestDto postRequestDto) {
        try {
            Post post = postRepository.findById(postRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

            post.setContent(postRequestDto.getContent());
            post.setImageUrl(postRequestDto.getImageUrl());

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
