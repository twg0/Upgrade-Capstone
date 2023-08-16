package com.twg0.upgradecapstone.device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceRegisterUserRequest {

	@NotNull
	private Long userId;

	@Builder
	public DeviceRegisterUserRequest(Long userId) {
		this.userId = userId;
	}
}
