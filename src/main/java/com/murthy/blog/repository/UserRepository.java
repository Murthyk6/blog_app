package com.murthy.blog.repository;

import com.murthy.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(@Param("username") String username);
    Optional<User> findByEmail(@Param("email") String email);
}
