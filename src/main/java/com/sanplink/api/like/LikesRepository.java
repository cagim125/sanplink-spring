package com.sanplink.api.like;


import com.sanplink.api.post.Post;
import com.sanplink.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional findByPostAndUser(Post post, User user);

    int countByPost(Post post);
    boolean existsByPostAndUserId(Post post, Long currentUserId);
}
