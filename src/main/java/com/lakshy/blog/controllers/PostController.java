package com.lakshy.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lakshy.blog.config.AppConstants;
import com.lakshy.blog.payloads.ApiResponse;
import com.lakshy.blog.payloads.PostDto;
import com.lakshy.blog.payloads.PostResponse;
import com.lakshy.blog.services.FileService;
import com.lakshy.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	//path from application.properties to  upload images
	@Value("${project.image}")
	private String PATH;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) 
	{
		PostDto savedPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(savedPost,HttpStatus.CREATED);
	}
	
	// get posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize
			)
	{
		PostResponse postResponse = this.postService.getPostsByCategory(categoryId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	// get posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> posts = this.postService.getPostsByUsers(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto post = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> posts = this.postService.getAllPosts();
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	// Admin only
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
	{
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted successfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		System.out.println("here");
		PostDto post = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	// pagination and sorting
	@GetMapping("/postsPage")
	public ResponseEntity<PostResponse> getAllPostsByPage(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SOTRT_BY, required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
		PostResponse postResponse = this.postService.getAllPostsByPage(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	} 
	
	// searching
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords)
	{
		List<PostDto> result = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
			) throws IOException
	{
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(PATH, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	// serve image using restapi
	@GetMapping(value ="post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(
			@PathVariable String imageName,
			HttpServletResponse response
			) throws IOException 
	{
		InputStream resource = this.fileService.getResources(PATH, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
