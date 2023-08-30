package com.murthy.blog.service.implementation;

import com.murthy.blog.model.Comment;
import com.murthy.blog.repository.CommentRepository;
import com.murthy.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(int id) {
        Optional<Comment> comment = commentRepository.findById(id);

        Comment theComment;

        if (comment.isPresent()) {
            theComment = comment.get();
        }
        else {

            throw new RuntimeException("Did not find comment id - " + id);
        }
        return theComment;
    }

    @Override
    public void delete(Comment commentToDelete) {
        commentRepository.delete(commentToDelete);
    }

    @Override
    public void deleteByPostId(int postId) {
        System.out.println("enter 3");
        commentRepository.deleteByPostId(postId);

    }
}
