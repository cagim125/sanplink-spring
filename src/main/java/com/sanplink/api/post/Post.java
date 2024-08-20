package com.sanplink.api.post;

import com.sanplink.api.dto.PostDto;
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

    public Post(PostDto postDto, User user){
        this.content = postDto.getContent();
        this.imageUrl = postDto.getImageUrl();
        this.user = user;
    }

}
