package com.murthy.blog.controller;

import com.murthy.blog.model.Post;
import com.murthy.blog.model.Tag;
import com.murthy.blog.model.User;
import com.murthy.blog.service.PostService;
import com.murthy.blog.service.TagService;
import com.murthy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private PostService postService;
    private UserService userService;

    @Autowired
    public HomeController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public String feedBlog(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "desc") String sortDirection,
            @ModelAttribute(name = "search") String search,
            @RequestParam(name = "author", required = false, defaultValue = "") List<String> checkedAuthors,
            @RequestParam(name = "tag", required = false, defaultValue = "") List<String> checkedTags,
            @RequestParam(name = "fromTimestamp", required = false) LocalDateTime fromTimestamp,
            @RequestParam(name = "toTimestamp", required = false) LocalDateTime toTimestamp,
            Model model) {
        int pageSize = 10;

        Sort sort;
        if (sortDirection.equals("asc")) {
            sort = Sort.by(Sort.Order.asc("publishedAt"));
        } else {
            sort = Sort.by(Sort.Order.desc("publishedAt"));
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        Page<Post> postsPage;
        Set<String> allAuthors = new HashSet<>();
        Set<String> allTags = new HashSet<>();

        if (search == null || search.isEmpty()) {
            postsPage = postService.allPost(checkedAuthors, checkedTags, fromTimestamp, toTimestamp, pageable);
            allAuthors = postService.getAllDistinctAuthor();
            for (Post post : postService.findAll()) {
                for (Tag tag : post.getTags()) {
                    allTags.add(tag.getName());
                }
            }
        } else {
            postsPage = postService.searchPostsWithFilters(search, checkedAuthors, checkedTags, fromTimestamp, toTimestamp, pageable);
            for (Post post : postService.searchPosts(search)) {
                allAuthors.add(post.getAuthor());
                for (Tag tag : post.getTags()) {
                    allTags.add(tag.getName());
                }
            }
        }

        int count = (int) postsPage.getTotalElements();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByEmail(authentication.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
        }

        model.addAttribute("count", count);
        model.addAttribute("blogs", postsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        model.addAttribute("checkedAuthors", checkedAuthors);
        model.addAttribute("checkedTags", checkedTags);
        model.addAttribute("fromTimestamp", fromTimestamp);
        model.addAttribute("toTimestamp", toTimestamp);
        model.addAttribute("search", search);
        model.addAttribute("allTags", allTags);
        model.addAttribute("allAuthors", allAuthors);
        model.addAttribute("sortDirection", sortDirection);

        return "blog/feed";
    }
}


