package com.BikkadIT.Blog_Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BikkadIT.Blog_Application.constants.AppConstants;
import com.BikkadIT.Blog_Application.payloads.ApiResponse;
import com.BikkadIT.Blog_Application.payloads.CommentsDto;
import com.BikkadIT.Blog_Application.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentsController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/posts/{postID}/comments")
	public ResponseEntity<CommentsDto>saveComment(@RequestBody CommentsDto commentsDto, @PathVariable Integer postId){
	
		CommentsDto createComment = this.commentService.createComment(commentsDto, postId);
		
		return new ResponseEntity<CommentsDto>(createComment,HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{cid}")
	public ApiResponse deleteComment(@PathVariable Integer cid) {

		this.commentService.deleteComment(cid);

		return new ApiResponse(AppConstants.SUCCESS, true);

	}

}
