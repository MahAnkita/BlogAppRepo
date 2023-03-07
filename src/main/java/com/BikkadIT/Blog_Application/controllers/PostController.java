package com.BikkadIT.Blog_Application.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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

import com.BikkadIT.Blog_Application.constants.AppConstants;
import com.BikkadIT.Blog_Application.payloads.ApiResponse;
import com.BikkadIT.Blog_Application.payloads.PostDto;
import com.BikkadIT.Blog_Application.payloads.PostResponse;
import com.BikkadIT.Blog_Application.services.FileService;
import com.BikkadIT.Blog_Application.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	
	@Value("$(project.image)")
	private String path;


	Logger logger = LoggerFactory.getLogger(PostController.class);

	

	/**
	 * @author Ankita
	 * @apiNote Add the post details
	 * @since 1.0
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	// POST: create Post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> savePost(@Valid @RequestBody PostDto postdto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		logger.info("Initiating service call for save post");

		PostDto createPost = this.postService.createPost(postdto, categoryId, userId);

		logger.info("Completed service call for save post");

		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	/**
	 * @author Ankita
	 * @apiNote Get list of all posts by User
	 * @since 1.0
	 * @param userId
	 * @return
	 */

	// GET: Get all Posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

		logger.info("Initiating service call for Get all Posts by user");
		List<PostDto> allPostsByUser = this.postService.getAllPostsByUser(userId);
		logger.info("Completed service call for Get all Posts by user");
		
		return new ResponseEntity<List<PostDto>>(allPostsByUser, HttpStatus.OK);
	}
	
	/**
	 * @author Ankita
	 * @apiNote Get list of all posts by Category
	 * @since 1.0
	 * @param categoryId
	 * @return
	 */

	// GET: Get all Posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		
		logger.info("Initiating service call for Get all Posts by category");
		List<PostDto> allPostsByCategory = this.postService.getAllPostsByCategory(categoryId);
		logger.info("Completed service call for Get all Posts by category");
		
		return new ResponseEntity<List<PostDto>>(allPostsByCategory, HttpStatus.OK);
	}


	/**
	 * @author Ankita
	 * @apiNote Get list of all posts by postID
	 * @since 1.0
	 * @param postId
	 * @return
	 */
	// GET: Get Post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable Integer postId) {

		logger.info("Initiating service call for Get all Posts by postId");
		PostDto post = this.postService.getPost(postId);
		logger.info("Completed service call for Get all Posts by postId");
		
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	/**
	 * @author Ankita
	 * @apiNote Get list of all posts
	 * @since 1.0
	 * @return
	 */

	// GET: Get all Posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam( value="pageSize",defaultValue=AppConstants.PAGE_SIZE, required=false)Integer pageSize,
			@RequestParam( value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER, required=false)Integer pageNumber,
			@RequestParam( value="sortBy",defaultValue=AppConstants.SORT_BY_PID, required=false)String sortBy,
			@RequestParam( value="sortDir",defaultValue=AppConstants.SORT_DIR, required=false)String sortDir){
		
		logger.info("Initiating service call for Get all Posts");
		PostResponse allPosts = this.postService.getAllPosts(pageSize,pageNumber,sortBy,sortDir);
		logger.info("Completed service call for Get all Posts");

		return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
	}

	/**
	 * @author Ankita
	 * @apiNote Update post details
	 * @since 1.0
	 * @param postId
	 * @param postdto
	 * @return
	 */
	// UPDATE: Update the Post details
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postdto, @PathVariable Integer postId) {

		logger.info("Initiating service call for Update the Post details with:{}",postId);
		PostDto updatePost = this.postService.updatePost(postdto, postId);
		logger.info("Completed service call for Update the Post details");

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	
	/**
	 * @author Ankita
	 * @apiNote delete posts by postID
	 * @since 1.0
	 * @param postId
	 * @return
	 */

	//DELETE: delete post by Id
	@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePostById(@PathVariable Integer postId) {
			
			logger.info("Initiating service call for delete post by Id");
			this.postService.deletePost(postId);
			logger.info("Completed service call for delete post by Id");
			
			return new ApiResponse(AppConstants.SUCCESS, true);
		}
	
	/**
	 * @author Ankita
	 * @apiNote Search post by keyword
	 * @since 1.0
	 * @param userId
	 * @return
	 */

	//DELETE: Search post by keyword
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>>searchPostByTitle(@PathVariable String keywords){
		
		logger.info("Initiating service call for Search post by keyword");
		List<PostDto> searchPost = this.postService.searchPost(keywords);
		logger.info("Completed service call for Search user by keyword");
		
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
	}

	
	//post image upload
	
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException
		{
		     PostDto postDto = this.postService.getPost(postId);	

			String filename = this.fileService.uploadImage(path, image);
		     postDto.setImageName(filename);
		     PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		 return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
		}
		
		//method to serve file
		@GetMapping(value = "/post/image/{imagename}",produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imagename") String imagename,HttpServletResponse response) throws IOException
		{
			InputStream resource = this.fileService.getResource(path, imagename);
			
	       response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	       StreamUtils.copy(resource,response.getOutputStream());
		}
		
		
}
