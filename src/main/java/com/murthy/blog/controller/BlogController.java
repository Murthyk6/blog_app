package com.murthy.blog.controller;

import com.murthy.blog.model.Comment;
import com.murthy.blog.model.Post;
import com.murthy.blog.model.Tag;
import com.murthy.blog.model.User;
import com.murthy.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;
    private final CommentService commentService;
    private final TagService tagService;
    private final PostTagService postTagService;
    private final UserService userService;

    @Autowired
    public BlogController(PostService postService, CommentService commentService,
                          TagService tagService, PostTagService postTagService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.tagService = tagService;
        this.postTagService = postTagService;
        this.userService = userService;
    }

    @GetMapping("/full_post")
    public String showFullBlogPost(@RequestParam("postId") int postId, Model model, Principal principal) {
        Post post = postService.findByIdWithTags(postId);
        if (post == null) {
            return "redirect:/feed";
        }

        List<Comment> comments = commentService.getCommentsByPostId(postId);
        Comment newComment = new Comment();

        model.addAttribute("user", post.getUser());
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", newComment);

        return "blog/full_blog";
    }

    @PostMapping("/edit")
    public String editBlogPost(@RequestParam("postId") int postId, Model model) {
        Post post = postService.findById(postId);
        post.setUpdatedAt(LocalDateTime.now());

        Set<Tag> tags = postTagService.getTagsByPostId(postId);
        List<String> tagStr = new ArrayList<>();
        for (Tag tag : tags) {
            tagStr.add(tag.getName());
        }

        boolean published = post.isPublished();

        model.addAttribute("published", published);
        model.addAttribute("tagStr", String.join(" ", tagStr));
        model.addAttribute("post", post);

        return "blog/update";
    }

    @PostMapping("/save")
    public String saveBlog(Model model, @RequestParam("tagStr") String tagStr,
                           @RequestParam("saveType") String saveType,
                           @ModelAttribute("post") Post post,
                           Principal principal) {
        Set<Tag> tagSet = new HashSet<>();
        String[] stringArray = tagStr.split(" ");

        Optional<User> user = userService.findByEmail(principal.getName());
        post.setUser(user.get());

        for (String tagName : stringArray) {
            Tag existingTag = tagService.findByName(tagName);
            if (existingTag != null) {
                tagSet.add(existingTag);
            } else {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                tagSet.add(newTag);
            }
        }

        int fullStop = post.getContent().indexOf(".");
        post.setExcerpt(post.getContent().substring(0, fullStop!=0 ? fullStop : 20));
        post.setTags(tagSet);

        if (saveType.length() == 5) {
            post.setPublished(false);
        } else {
            post.setPublished(true);
            post.setUpdatedAt(LocalDateTime.now());
            if (post.getPublishedAt() == null) {
                post.setPublishedAt(LocalDateTime.now());
            }
        }

        postService.save(post);
        model.addAttribute("post", post);

        if (saveType.length() == 5) {
            return "redirect:/profile";
        } else {
            return "redirect:/blog/full_post?postId=" + post.getId();
        }
    }

    @GetMapping("/createPost")
    public String createBlog(Model model) {
        Post post = new Post();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByEmail(authentication.getName());

        post.setAuthor(user.get().getName());
        post.setCreatedAt(LocalDateTime.now());
        model.addAttribute("post", post);

        return "blog/create";
    }

    @PostMapping("/delete")
    @Transactional
    public String deletePost(@RequestParam("postId") int postId, Model model) {
        postService.deleteById(postId);
        return "redirect:/feed";
    }
}


