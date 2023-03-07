package com.BikkadIT.Blog_Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BikkadIT.Blog_Application.entities.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer>{

}
