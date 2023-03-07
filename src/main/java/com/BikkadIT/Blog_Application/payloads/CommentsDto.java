package com.BikkadIT.Blog_Application.payloads;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
	
	private Integer cid;
	
	private String content;

}
