package com.BikkadIT.Blog_Application.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BikkadIT.Blog_Application.entities.Category;
import com.BikkadIT.Blog_Application.entities.Post;
import com.BikkadIT.Blog_Application.entities.User;
import com.BikkadIT.Blog_Application.exceptions.ResourceNotFoundException;
import com.BikkadIT.Blog_Application.payloads.PostDto;
import com.BikkadIT.Blog_Application.payloads.PostResponse;
import com.BikkadIT.Blog_Application.repositories.CategoryRepo;
import com.BikkadIT.Blog_Application.repositories.PostRepository;
import com.BikkadIT.Blog_Application.repositories.UserRepository;
import com.BikkadIT.Blog_Application.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {

		logger.info("Initiating dao call for save post");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("userId", "userId", userId));

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("categoryId", "categoryId", categoryId));

		Post post = this.modelmapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(cat);
		post.setUser(user);

		Post save = this.postRepository.save(post);

		logger.info("Completed dao call for save post");
		return this.modelmapper.map(save, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		logger.info("Initiating dao call for Update the Post details with:{}", postId);
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = this.postRepository.save(post);
		logger.info("Completed dao call for Update the Post details");

		return this.modelmapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {

		logger.info("Initiating dao call for delete user by Id");
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		logger.info("Completed dao call for delete user by Id");

		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPost(Integer postId) {

		logger.info("Initiating dao call for Get all Posts by postId");
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		logger.info("Completed dao call for Get all Posts by postId");

		return this.modelmapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageSize,Integer pageNumber, String sortBy,String sortDir) {
		
		logger.info("Initiating dao call for Get all Posts");
		
		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending()) ;
	
		Pageable of = PageRequest.of(pageNumber, pageSize,sort);
		Page <Post> pagePost =this.postRepository.findAll(of);
		List<Post> allposts = pagePost.getContent();
		
		//List<Post> allposts = this.postRepository.findAll();
		List<PostDto> list = allposts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		logger.info("Completed dao call for Get all Posts");
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(list);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		logger.info("Completed dao call for Get all Posts");
		return postResponse;
		
		
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {

		logger.info("Initiating dao call for Get all Posts by category");
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDto> list = posts.stream().map((post) -> this.modelmapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		logger.info("Completed dao call for Get all Posts by category");

		return list;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {

		logger.info("Initiating dao call for Get all Posts by user");

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDto> list = posts.stream().map((post) -> this.modelmapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		logger.info("Completed dao call for Get all Posts by user");

		return list;
	}

	@Override
	public List<PostDto> searchPost(String keywords) {
		
		logger.info("Initiating dao call for Search post by keyword");
		List<Post> titlelist = this.postRepository.searchByTitle("%"+keywords+"%");
		List<PostDto> list = titlelist.stream().map((post)->this.modelmapper
				.map(post, PostDto.class)).collect(Collectors.toList());
		logger.info("Completed dao call for Search user by keyword");
		return list;
	}

}
