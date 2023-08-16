package com.twg0.upgradecapstone.device.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class DisabledResponse {

	private LocalDateTime createdTime;

	@QueryProjection
	public DisabledResponse(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

}
