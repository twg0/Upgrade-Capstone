package com.twg0.upgradecapstone.file.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileCreateRequest {
		
	@NotNull
	private Long villageId;
	@NotNull
	@Size(max = 30)
	private String title;
	@NotNull
	@Size(max = 100)
	private String contents;
}
