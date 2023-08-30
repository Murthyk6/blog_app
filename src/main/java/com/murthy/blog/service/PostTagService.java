package com.murthy.blog.service;

import com.murthy.blog.model.Tag;
import com.murthy.blog.repository.PostTagRepository;

import java.util.Set;

public interface PostTagService {
    Set<Tag> getTagsByPostId(int postId);
}
