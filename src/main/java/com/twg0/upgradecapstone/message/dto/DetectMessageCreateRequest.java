package com.twg0.upgradecapstone.message.dto;

import java.util.List;

import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.message.domain.DetectMessage;
import com.twg0.upgradecapstone.message.domain.MessageFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetectMessageCreateRequest implements MessageCreateRequsetInterface {

	@NotNull
	private Device device;
	@NotNull
	private Double temperature;
	@NotNull
	private Double humidity;
	@NotNull
	private Boolean detectionVibration;
	@NotNull
	private Boolean detectionGasLeak;
	@NotNull
	private Boolean detectionAbnormalness;

	@Builder
	public DetectMessageCreateRequest(Device device, Double temperature, Double humidity,
		Boolean detectionVibration, Boolean detectionGasLeak, Boolean detectionAbnormalness) {
		this.device = device;
		this.temperature = temperature;
		this.humidity = humidity;
		this.detectionVibration = detectionVibration;
		this.detectionGasLeak = detectionGasLeak;
		this.detectionAbnormalness = detectionAbnormalness;
	}

	public DetectMessageCreateRequest(List<String> payload) {
		this.temperature = Double.valueOf(payload.get(MessageFormat.TEMPERATURE.getIndex()));
		this.humidity = Double.valueOf(payload.get(MessageFormat.HUMIDITY.getIndex()));
		this.detectionVibration = Boolean.valueOf(payload.get(MessageFormat.VIBRATION.getIndex()));
		this.detectionGasLeak = Boolean.valueOf(payload.get(MessageFormat.GASLEAK.getIndex()));
		this.detectionAbnormalness = Boolean.valueOf(payload.get(MessageFormat.ABNORMALNESS.getIndex()));
	}

	public DetectMessage toEntity() {
		return DetectMessage.builder()
			.device(device)
			.temperature(temperature)
			.humidity(humidity)
			.detectionVibration(detectionVibration)
			.detectionGasLeak(detectionGasLeak)
			.detectionAbnormalness(detectionAbnormalness)
			.build();
	}
}
