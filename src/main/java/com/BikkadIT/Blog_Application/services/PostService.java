package com.BikkadIT.Blog_Application.services;

import java.util.List;

import com.BikkadIT.Blog_Application.payloads.PostDto;
import com.BikkadIT.Blog_Application.payloads.PostResponse;

public interface PostService {
	
		//create
		PostDto createPost(PostDto postDto, Integer categoryId, Integer userId ); //factory design pattern

		//Update
		PostDto updatePost(PostDto postDto, Integer postId );
		
		//delete by Id
		void deletePost(Integer postId);
		
		//get by Id
		PostDto getPost(Integer postId );
		
		//get All Posts
		PostResponse getAllPosts(Integer pageSize,Integer pageNumber, String sortBy,String sortDir);
		

		//get All Posts by Category
		List<PostDto> getAllPostsByCategory(Integer categoryId);
		
		//get All Posts by Category
		List<PostDto> getAllPostsByUser(Integer userId);
		
		//search a Post
		List<PostDto>searchPost(String keywords);

	
}
