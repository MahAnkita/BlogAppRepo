package com.BikkadIT.Blog_Application.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	//we took dto for showing the content of entity class on the UI only which is compulsory
	
	private Integer id;
	
	//Here we add validation to the data only which is shown to the user in UI
	
	@NotEmpty
	@Size(min=4, message="Enter username with Minimum 4 Characters...!!!")
	private String name;
	
	@Email(message="Email address is not valid...!!!")
	private String email;
	
	@NotEmpty
	@Size(min=4, max=10, message="Password must be minimum 4 & Maximum 8 characters long")
	private String password;
	
	@NotEmpty
	@Size(max=50,message="Describe yourself in 50 words")
	private String about;


}
