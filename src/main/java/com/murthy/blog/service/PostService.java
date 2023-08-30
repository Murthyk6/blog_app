package com.murthy.blog.service;

import com.murthy.blog.model.Post;
import com.murthy.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface PostService {
    Post findById(int id);
    void save(Post post);
    void deleteById(int id);
    Post findByIdWithTags(int postId);
    Set<String> getAllDistinctAuthor();
    List<Post> publishedPosts(User user);
    List<Post> draftPosts(User user);
    List<Post> findAll();
    List<Post> searchPosts(String searchTerm);
    Page<Post> searchPostsWithFilters(String searchTerm,List<String> authors, List<String> tags,LocalDateTime fromTimestamp,
                                      LocalDateTime toTimestamp,Pageable pageable);
    Page<Post> allPost(List<String> authors, List<String> tags, LocalDateTime fromTimestamp, LocalDateTime toTimestamp,
                       Pageable pageable);
}
