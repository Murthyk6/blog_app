package com.murthy.blog.service;

import com.murthy.blog.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(@Param("email") String email);

}
