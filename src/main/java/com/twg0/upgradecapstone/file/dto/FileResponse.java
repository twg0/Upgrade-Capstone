package com.twg0.upgradecapstone.file.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.timcook.capstone.file.domain.File;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileResponse {

	private String title;
	private String contents;
	private LocalDateTime createdTime;

	@Builder
	@QueryProjection
	public FileResponse(String title, String contents, LocalDateTime createdTime) {
		this.title = title;
		this.contents = contents;
		this.createdTime = createdTime;
	}
	
	public static FileResponse from(File file) {
		return FileResponse.builder()
						.title(file.getTitle())
						.contents(file.getContents())
						.createdTime(file.getCreatedTime())
						.build();
	}
	
}
