package com.twg0.upgradecapstone.message.dto;

import javax.validation.constraints.NotNull;

import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.message.domain.UrgentMessage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UrgentMessageCreateRequest implements MessageCreateRequsetInterface{
		
	@NotNull
	private Device device;

	public UrgentMessage toEntity() {
		return UrgentMessage.builder()
						.device(device)
						.build();
	}
	
}
