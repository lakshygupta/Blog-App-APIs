package com.lakshy.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotBlank
	@Size(min = 3,message = "length of title should be minimum 3")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "length should be minimum 10")
	private String categoryDescription;
}
