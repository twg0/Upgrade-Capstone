package com.twg0.upgradecapstone.message.factory;

import java.util.List;

import com.twg0.upgradecapstone.message.domain.MessageType;
import com.twg0.upgradecapstone.message.dto.DetectMessageCreateRequest;
import com.twg0.upgradecapstone.message.dto.MessageCreateRequsetInterface;
import com.twg0.upgradecapstone.message.dto.ReplyMessageCreateRequest;
import com.twg0.upgradecapstone.message.dto.SettingRequestMessage;
import com.twg0.upgradecapstone.message.dto.UrgentMessageCreateRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageCreateRequestFactory extends AbstractMessageCreateRequestFactory {

	@Override
	public MessageCreateRequsetInterface create(MessageType messageType, List<String> payload) {
		log.info("MESSAGE_TYPE : {}", messageType.name());

		switch (messageType) {
			case DETECT:
				return createDetectMessageCreateRequest(payload);
			case URGENT:
				return createUrgentMessageCreateRequest(payload);
			case REPLY:
				return createReplyMessageCreateRequest(payload);
			case SETTING:
				return createSettingRequestMessage(payload);
			default:
				throw new IllegalArgumentException("잘못된 메세지 형태입니다.");
		}
	}

	private DetectMessageCreateRequest createDetectMessageCreateRequest(List<String> payload) {
		DetectMessageCreateRequest detectMessageCreateRequest = new DetectMessageCreateRequest(payload);
		return detectMessageCreateRequest;
	}

	private UrgentMessageCreateRequest createUrgentMessageCreateRequest(List<String> payload) {
		UrgentMessageCreateRequest urgentMessageCreateRequest = new UrgentMessageCreateRequest();
		return urgentMessageCreateRequest;
	}

	private ReplyMessageCreateRequest createReplyMessageCreateRequest(List<String> payload) {
		ReplyMessageCreateRequest replyMessageCreateRequest = new ReplyMessageCreateRequest(payload);
		return replyMessageCreateRequest;
	}

	private SettingRequestMessage createSettingRequestMessage(List<String> payload) {
		SettingRequestMessage settingRequestMessage = new SettingRequestMessage(payload);
		return settingRequestMessage;
	}
}
