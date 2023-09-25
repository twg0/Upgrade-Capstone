package com.twg0.upgradecapstone.message.dto;

import java.time.LocalDateTime;

import com.twg0.upgradecapstone.message.domain.DetectMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetectMessageResponse {

	private double temperature;
	private double humidity;
	private LocalDateTime detect_time;

	public static DetectMessageResponse from(DetectMessage detectMessage) {
		return DetectMessageResponse.builder()
			.temperature(detectMessage.getTemperature())
			.humidity(detectMessage.getHumidity())
			.detect_time(detectMessage.getCreatedTime())
			.build();
	}
}
