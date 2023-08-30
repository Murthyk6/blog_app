package com.murthy.blog.repository;

import com.murthy.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment , Integer> {
    List<Comment> getCommentsByPostId(int postId);
    void deleteByPostId(int postId);
}
