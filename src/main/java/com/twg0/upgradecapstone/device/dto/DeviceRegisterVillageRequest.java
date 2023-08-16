package com.twg0.upgradecapstone.device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceRegisterVillageRequest {

	@NotNull
	private Long villageId;

	@Builder
	public DeviceRegisterVillageRequest(Long villageId) {
		this.villageId = villageId;
	}
}
