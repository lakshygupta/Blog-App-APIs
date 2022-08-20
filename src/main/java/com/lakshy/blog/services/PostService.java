package com.lakshy.blog.services;

import java.util.List;

import com.lakshy.blog.payloads.PostDto;
import com.lakshy.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
	
	List<PostDto> getAllPosts();
	
	PostDto getPostById(Integer postId);
	
	// implementing pagination on getPostsByCategory, [view getPostsByUsers implementation for naive approach]
	//get all posts by category
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
	// get all posts by User
	List<PostDto> getPostsByUsers(Integer userId);
	
	// search title posts by keyword
	List<PostDto> searchPost(String keyword);
	
	 // pagination using JpaRepository -> to send its response we have a seperate PostResponse class in PayLoad
	PostResponse getAllPostsByPage(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
