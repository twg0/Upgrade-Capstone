package com.twg0.upgradecapstone.device.dto;

import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.village.domain.Village;

import jakarta.validation.constraints.NotNull;
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
