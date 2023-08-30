package com.murthy.blog.controller;

import com.murthy.blog.model.Post;
import com.murthy.blog.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.murthy.blog.model.User;
import com.murthy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "register-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute(name = "user") User user,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "confirmPassword") String confirmPassword,
                           Model model) {
        Optional<User> existing = userService.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            model.addAttribute("error", "User with this email already exists");
            return "register-form";
        }

        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "password not matched");
            return "register-form";
        }
        password = "{noop}" + password;

        user.setPassword(password);
        user.setActive(1);
        user.setRole("ROLE_AUTHOR");
        userService.createUser(user);
        return "redirect:/userLogin";
    }

    @GetMapping("/profile")
    public String profile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findByEmail(authentication.getName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> publishedPosts = postService.publishedPosts(user);
            List<Post> draftPosts = postService.draftPosts(user);

            model.addAttribute("publishedPosts", publishedPosts);
            model.addAttribute("draftPosts",draftPosts);
            return "user/profile";
        }
        return "blog/feed";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

}
