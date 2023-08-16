package com.twg0.upgradecapstone.device.dto;

import javax.validation.constraints.NotNull;

import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.user.domain.User;
import com.timcook.capstone.village.domain.Village;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpdateRequest {

	@NotNull
	private Long memberId;
	@NotNull
	private Long villageId;
	
	public static Device toEntity(User user, Village village) {
		return Device.builder()
					.user(user)
					.village(village)
					.build();
	}
	
}
