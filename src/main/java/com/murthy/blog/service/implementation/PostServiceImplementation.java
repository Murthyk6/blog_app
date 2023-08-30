package com.murthy.blog.service.implementation;

import com.murthy.blog.model.Post;
import com.murthy.blog.model.User;
import com.murthy.blog.repository.PostRepository;
import com.murthy.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostServiceImplementation implements com.murthy.blog.service.PostService {
    private PostRepository postRepository;
    private CommentService commentService;

    public PostServiceImplementation() {
    }

    @Autowired
    public PostServiceImplementation(PostRepository postRepository , CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
    }

    @Override
    public Post findById(int id) {

        Optional<Post> post = postRepository.findById(id);
        Post thePost;

        if (post.isPresent()) {
            thePost = post.get();
        }
        else {
            thePost = new Post();
        }
        return thePost;
    }

    @Override
    public void save(Post post) {

        postRepository.save(post);
    }

    @Override
    public void deleteById(int id) {
        commentService.deleteByPostId(id);
        postRepository.deleteById(id);
    }

    @Override
    public Post findByIdWithTags(int postId) {
        return postRepository.findPostWithTagsById(postId);
    }

    @Override
    public List<Post> searchPosts(String searchTerm) {
        return postRepository.search(searchTerm);
    }

    @Override
    public Set<String> getAllDistinctAuthor() {
        return postRepository.getAllDistinctAuthor();
    }

    @Override
    public List<Post> publishedPosts(User user) {
        return postRepository.publishedPosts(user);
    }

    @Override
    public List<Post> draftPosts(User user) {
        return postRepository.draftPosts(user);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> allPost(List<String> authors, List<String> tags, LocalDateTime fromTimestamp,
                              LocalDateTime toTimestamp, Pageable pageable) {

        int isEmptyAuthor = authors.isEmpty() ? 1 : 0 ;
        int isEmptyTag = tags.isEmpty() ? 1 : 0 ;

        if (toTimestamp == null) {
            toTimestamp = LocalDateTime.now();
        }

       if(fromTimestamp == null) {
           Optional<LocalDateTime> timestamp = postRepository.findOldestPublishedDate();
           fromTimestamp = timestamp.get();
       }
        return postRepository.allPost(isEmptyAuthor,isEmptyTag,authors,tags,fromTimestamp,toTimestamp,pageable);
    }

    @Override
    public Page<Post> searchPostsWithFilters(String searchTerm, List<String> authors,List<String> tags,
                                             LocalDateTime fromTimestamp,LocalDateTime toTimestamp,Pageable pageable) {
        int isEmptyAuthor = authors.isEmpty() ? 1 : 0 ;
        int isEmptyTag = tags.isEmpty() ? 1 : 0 ;

        if (toTimestamp == null) {
            toTimestamp = LocalDateTime.now();
        }
        if(fromTimestamp == null) {
            Optional<LocalDateTime> timestamp = postRepository.findOldestPublishedDate();
            fromTimestamp = timestamp.get();
        }
        return postRepository.searchPostsWithFilters(searchTerm,isEmptyAuthor,isEmptyTag,authors,tags,fromTimestamp,
                toTimestamp,pageable);
    }
}
