package com.twg0.upgradecapstone.notification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {

	private String token;
	private String title;
	private String body;
	
	@Builder
	public NotificationRequest(String token, String title, String body) {
		this.token = token;
		this.title = title;
		this.body = body;
	}
}
