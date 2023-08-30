package com.murthy.blog.repository;

import com.murthy.blog.model.Post;
import com.murthy.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    List<Post> findAll();

    Optional<Post>findById(int id);
    @Query("SELECT p FROM Post p WHERE " +
            "p.isPublished = TRUE AND"+
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "EXISTS (SELECT pt FROM PostTag pt JOIN pt.tag t WHERE pt.post = p AND LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))) " )
    List<Post> search(@Param("searchTerm") String searchTerm);

    Post findPostWithTagsById(@Param("id") int id);

    @Query("SELECT DISTINCT p.author FROM Post p " +
            "WHERE p.isPublished = TRUE")
    Set<String> getAllDistinctAuthor();

    @Query("SELECT p FROM Post p WHERE " +
            "p.isPublished = TRUE " +
            "AND p.user = :currentUser")
    List<Post> publishedPosts(@Param("currentUser") User currentUser);

    @Query("SELECT p FROM Post p WHERE " +
            "p.isPublished = FALSE " +
            "AND p.user = :currentUser")
    List<Post> draftPosts(@Param("currentUser") User currentUser);

    @Query("SELECT p FROM Post p WHERE " +
            "p.isPublished = TRUE AND"+
            "(LOWER(p.title) LIKE " +
            "LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "EXISTS (SELECT pt FROM PostTag pt JOIN pt.tag t WHERE pt.post = p AND LOWER(t.name) LIKE " +
            "LOWER(CONCAT('%', :searchTerm, '%')))) " +
            "AND (:isAuthor = 1 OR p.author IN :authors)"+
            "AND (:isTags = 1 OR EXISTS (SELECT pt2 FROM PostTag pt2 JOIN pt2.tag t2 WHERE pt2.post = p AND t2.name IN :tags))"+
            "AND (p.publishedAt BETWEEN :fromTimestamp AND :toTimestamp)")
    Page<Post> searchPostsWithFilters(@Param("searchTerm") String searchTerm,
                                     @Param("isAuthor") int isAuthor,
                                     @Param("isTags")  int isTags,
                                     @Param("authors") List<String> authors,
                                     @Param("tags") List<String> tags,
                                     @Param("fromTimestamp") LocalDateTime fromTimestamp,
                                     @Param("toTimestamp") LocalDateTime toTimestamp,
                                     Pageable pageable);

    @Query("SELECT p FROM Post p WHERE "+
            "p.isPublished = TRUE AND"+
            "(:isAuthor = 1 OR p.author IN :authors)" +
            "AND (:isTags = 1 OR EXISTS (SELECT pt2 FROM PostTag pt2 JOIN pt2.tag t2 WHERE pt2.post = p AND t2.name IN :tags))"+
            "AND (p.publishedAt BETWEEN :fromTimestamp AND :toTimestamp)")
    Page<Post> allPost(@Param("isAuthor") int isAuthor,
                       @Param("isTags")  int isTags,
                       @Param("authors") List<String> authors,
                       @Param("tags") List<String> tags,
                       @Param("fromTimestamp") LocalDateTime fromTimestamp,
                       @Param("toTimestamp") LocalDateTime toTimestamp,
                       Pageable pageable);

    @Query("SELECT MIN(p.publishedAt) FROM Post p")
    Optional<LocalDateTime> findOldestPublishedDate();
}
