package com.murthy.blog.controller;

import com.murthy.blog.model.Comment;
import com.murthy.blog.model.Post;
import com.murthy.blog.service.CommentService;
import com.murthy.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @PostMapping("/save")
    public String saveComment(@RequestParam("postId") int postId, @ModelAttribute("comment") Comment comment) {
        comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        if (comment.getCreatedAt() == null) {
            comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        Post post = postService.findById(postId);
        comment.setPost(post);
        commentService.save(comment);
        return "redirect:/blog/full_post?postId=" + postId;
    }

    @PostMapping("/delete")
    public String deleteCommentAndRedirect(@RequestParam("commentId") int commentId, @RequestParam("postId") int postId) {
        Comment commentToDelete = commentService.findById(commentId);
        if (commentToDelete != null) {
            commentService.delete(commentToDelete);
        }
        return "redirect:/blog/full_post?postId=" + postId;
    }

    @PostMapping("/edit")
    public String updateComment(@RequestParam("commentId") int commentId,
                                @RequestParam("postId") int postId, Model model) {
        Comment comment = commentService.findById(commentId);
        if (comment != null) {
            comment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            model.addAttribute("comment", comment);
            model.addAttribute("postId", postId);
        }
        return "comment/edit_comment";
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam("postId") int postId, @RequestParam("commentId") int commentId,
                                @ModelAttribute("comment") Comment updatedComment) {
        Comment existingComment = commentService.findById(commentId);
        if (existingComment != null) {
            existingComment.setName(updatedComment.getName());
            existingComment.setEmail(updatedComment.getEmail());
            existingComment.setTheComment(updatedComment.getTheComment());
            existingComment.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            commentService.save(existingComment);
        }
        return "redirect:/blog/full_post?postId=" + postId;
    }
}


