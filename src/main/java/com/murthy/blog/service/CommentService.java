package com.murthy.blog.service;

import com.murthy.blog.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPostId(int postId);
    void save(Comment comment);
    void deleteById(int id);
    Comment findById(int id);
    void delete(Comment commentToDelete);
    void deleteByPostId(int postId);
}
