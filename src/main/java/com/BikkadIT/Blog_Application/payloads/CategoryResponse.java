package com.BikkadIT.Blog_Application.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryResponse {

		private List<CategoryDto> content;
		private Integer pageNumber;
		private Integer pageSize;
		private Long totalElements;
		private Integer totalPages;
		private boolean lastPage;
	

}
