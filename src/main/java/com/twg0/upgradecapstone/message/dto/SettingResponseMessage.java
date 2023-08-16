package com.twg0.upgradecapstone.message.dto;

import com.timcook.capstone.message.domain.MessageType;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SettingResponseMessage {

	private String username;
	private Long deviceId;
	private Long villageId;
	
	@Builder
	public SettingResponseMessage(String username, Long deviceId, Long villageId) {
		this.username = username;
		this.deviceId = deviceId;
		this.villageId = villageId;
	}
	
	public static String connectFailPayload() {
		return MessageType.SETTING.name() + "/-1"; 
	}
	
	public static String connectSuccessPayload(SettingResponseMessage message) {
		return MessageType.SETTING.name() + "/" + message.getDeviceId() + "/" + message.getUsername() + "/" + message.getVillageId();
	}
}
