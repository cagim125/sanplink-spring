package com.sanplink.api.post;

import com.sanplink.api.dto.PostRequestDto;
import com.sanplink.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;
    private String imageUrl;

    public Post(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
    }

    public Post(PostRequestDto postRequestDto, User user){
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
        this.user = user;
    }

}
