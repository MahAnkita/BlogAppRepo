package com.BikkadIT.Blog_Application.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="post_id")
	private Integer postId;
	
	@Column(name="post_title")
	private String postTitle;
	
	@Column(name="post_content")
	private String postContent;
	
	@Column(name="image_name")
	private String imageName;
	
	@Column(name="added_date")
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy ="post",cascade=CascadeType.ALL)
	private Set<Comments> comments = new HashSet();
}
