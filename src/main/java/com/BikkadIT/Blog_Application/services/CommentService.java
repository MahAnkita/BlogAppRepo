package com.BikkadIT.Blog_Application.services;

import com.BikkadIT.Blog_Application.payloads.CommentsDto;

public interface CommentService {

	CommentsDto createComment (CommentsDto commentsDto, Integer postId);
	
	void deleteComment (Integer cid);
}
