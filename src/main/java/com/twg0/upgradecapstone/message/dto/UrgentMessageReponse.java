package com.twg0.upgradecapstone.message.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.timcook.capstone.message.domain.UrgentMessage;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class UrgentMessageReponse {

	private Long userId;
	private LocalDateTime createdTime;
	
	@QueryProjection
	public UrgentMessageReponse(Long userId, LocalDateTime createdTime) {
		this.userId = userId;
		this.createdTime = createdTime;
	}
	
	public static UrgentMessageReponse from(UrgentMessage urgentMessage) {
		return UrgentMessageReponse.builder()
					.userId(urgentMessage.getDevice().getUser().getId())
					.createdTime(urgentMessage.getCreatedTime())
					.build();
	}
}
