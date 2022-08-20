package com.lakshy.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements; // total records
	private int totalPages; // total pages as per calculation of records
	private boolean lastPage;
	
}
