package com.murthy.blog.repository;

import com.murthy.blog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag , Integer> {
    Tag findByName(String name);

    @Query("SELECT t.name FROM Tag t")
    List<String> findAllTagNames();
}
