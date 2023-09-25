package com.twg0.upgradecapstone.file.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
