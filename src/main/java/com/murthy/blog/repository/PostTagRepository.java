package com.murthy.blog.repository;

import com.murthy.blog.model.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Integer> {
    List<PostTag> findByPostId(int postId);
}
