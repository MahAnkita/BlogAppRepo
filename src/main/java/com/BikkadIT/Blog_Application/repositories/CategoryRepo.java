package com.BikkadIT.Blog_Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BikkadIT.Blog_Application.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
