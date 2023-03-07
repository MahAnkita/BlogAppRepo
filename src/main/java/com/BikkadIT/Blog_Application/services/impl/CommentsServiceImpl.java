package com.BikkadIT.Blog_Application.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BikkadIT.Blog_Application.entities.Comments;
import com.BikkadIT.Blog_Application.entities.Post;
import com.BikkadIT.Blog_Application.exceptions.ResourceNotFoundException;
import com.BikkadIT.Blog_Application.payloads.CommentsDto;
import com.BikkadIT.Blog_Application.repositories.CommentsRepo;
import com.BikkadIT.Blog_Application.repositories.PostRepository;
import com.BikkadIT.Blog_Application.services.CommentService;

@Service
public class CommentsServiceImpl implements CommentService {

	@Autowired
	private CommentsRepo commentsRepo;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Override
	public CommentsDto createComment(CommentsDto commentsDto, Integer postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id", postId));
		
		Comments comments = this.modelmapper.map(commentsDto, Comments.class);
		
		comments.setPost(post);
		
		Comments savedComment = this.commentsRepo.save(comments);
		
		return this.modelmapper.map(savedComment, CommentsDto.class);
		
	}

	@Override
	public void deleteComment(Integer cid) {
		
		Comments comments = this.commentsRepo.findById(cid).orElseThrow(()->new ResourceNotFoundException("Comment","comment id", cid));
		
		this.commentsRepo.delete(comments);
		
	}

}
