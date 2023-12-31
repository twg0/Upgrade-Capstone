package com.twg0.upgradecapstone.device.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class ConfirmResponse {

	private Long fileId;
	private String fileTitle;
	private LocalDateTime createdTime;

	@QueryProjection
	public ConfirmResponse(Long fileId, String fileTitle, LocalDateTime createdTime) {
		this.fileId = fileId;
		this.fileTitle = fileTitle;
		this.createdTime = createdTime;
	}
}
