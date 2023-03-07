package com.BikkadIT.Blog_Application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BikkadIT.Blog_Application.entities.Category;
import com.BikkadIT.Blog_Application.entities.Post;
import com.BikkadIT.Blog_Application.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByUser(User user);
	
	@Query("select p from Post p where  p.postTitle like :key")
	List <Post>searchByTitle(@Param("key") String postTitle);

}
