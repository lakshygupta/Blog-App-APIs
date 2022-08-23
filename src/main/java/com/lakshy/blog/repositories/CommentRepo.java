package com.lakshy.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshy.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
