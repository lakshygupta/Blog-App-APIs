package com.lakshy.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lakshy.blog.payloads.ApiResponse;
import com.lakshy.blog.payloads.CommentDto;
import com.lakshy.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId,@PathVariable Integer userId)
	{
		CommentDto createComment = this.commentService.createComment(comment, postId, userId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	// Admin only
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}
}
