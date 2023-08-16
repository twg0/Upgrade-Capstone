package com.twg0.upgradecapstone.message.dto;

import java.util.List;

import com.twg0.upgradecapstone.message.domain.MessageFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SettingRequestMessage implements MessageCreateRequsetInterface{

	private Long deviceId;
	private String phoneNumber;

	@Builder
	public SettingRequestMessage(Long deviceId, String phoneNumber) {
		this.deviceId = deviceId;
		this.phoneNumber = phoneNumber;
	}
	
	public SettingRequestMessage(List<String> payload) {
		this.deviceId = Long.valueOf(payload.get(MessageFormat.DEVICE_ID.getIndex()));
		this.phoneNumber = payload.get(MessageFormat.PHONE_NUMBER.getIndex());
	}
	
}
