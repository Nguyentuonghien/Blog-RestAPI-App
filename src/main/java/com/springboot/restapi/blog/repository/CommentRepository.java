package com.springboot.restapi.blog.repository;

import com.springboot.restapi.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // @Query("SELECT c FROM Comment c WHERE c.post.id = ?1")
    public List<Comment> findByPostId(long postId);

}

