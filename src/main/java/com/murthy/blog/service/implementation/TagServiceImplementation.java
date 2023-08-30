package com.murthy.blog.service.implementation;

import com.murthy.blog.model.Tag;
import com.murthy.blog.repository.TagRepository;
import com.murthy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImplementation implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImplementation(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findByName(String name) {
        Tag tag = tagRepository.findByName(name);
        return tag;
    }

    @Override
    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    @Override
    public List<String> findAllTagNames() {
        return tagRepository.findAllTagNames();
    }
}
