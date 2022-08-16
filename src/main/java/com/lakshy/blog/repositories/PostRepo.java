package com.lakshy.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lakshy.blog.entities.Category;
import com.lakshy.blog.entities.Post;
import com.lakshy.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	// custom finder methods
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
}
