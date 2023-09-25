package com.twg0.upgradecapstone.common.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FCMMessage {
	private boolean validate_only;
	private Message message;

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Message {
		private Notification notification;
		private String token;
	}

	@Builder
	@AllArgsConstructor
	@Getter
	public static class Notification {
		private String title;
		private String body;
		private String image;
	}

}
