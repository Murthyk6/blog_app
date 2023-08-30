package com.murthy.blog.service;

import com.murthy.blog.model.Tag;

import java.util.List;

public interface TagService {
    Tag findByName(String name);
    List<Tag> findAll();
    List<String> findAllTagNames();
}
