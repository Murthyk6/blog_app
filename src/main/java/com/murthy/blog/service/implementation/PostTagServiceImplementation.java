package com.murthy.blog.service.implementation;

import com.murthy.blog.model.PostTag;
import com.murthy.blog.model.Tag;
import com.murthy.blog.repository.PostTagRepository;
import com.murthy.blog.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostTagServiceImplementation implements PostTagService {
    private PostTagRepository postTagRepository;

    @Autowired
    public PostTagServiceImplementation(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    @Override
    public Set<Tag> getTagsByPostId(int postId) {
        Set<Tag> tags = new HashSet<>();
        List<PostTag> postTags = postTagRepository.findByPostId(postId);

        for (PostTag postTag : postTags) {
            tags.add(postTag.getTag());
        }
        return tags;
    }
}
