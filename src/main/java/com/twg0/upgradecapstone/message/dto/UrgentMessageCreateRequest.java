package com.twg0.upgradecapstone.message.dto;

import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.message.domain.UrgentMessage;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrgentMessageCreateRequest implements MessageCreateRequsetInterface {

	@NotNull
	private Device device;

	public UrgentMessage toEntity() {
		return UrgentMessage.builder()
			.device(device)
			.build();
	}

}
