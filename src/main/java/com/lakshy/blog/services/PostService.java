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
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// get all posts by User
	List<PostDto> getPostsByUsers(Integer userId);
	
	// search posts by keyword
	List<PostDto> searchPost(String keyword);
	
	 // pagination using JpaRepository -> to send its response we have a seperate PostResponse class in PayLoad
	PostResponse getAllPostsByPage(Integer pageNumber, Integer pageSize);
}
