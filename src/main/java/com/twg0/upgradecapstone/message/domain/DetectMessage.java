package com.twg0.upgradecapstone.message.domain;

import java.time.LocalDateTime;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.twg0.upgradecapstone.device.domain.Device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
public class DetectMessage extends AbstractMessage{

	@Id
	@GeneratedValue
	@Column(name = "MESSAGE_ID")
	@NotNull
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEVICE_ID")
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
	
	@CreatedDate
	private LocalDateTime createdTime;
	
	@Builder
	public DetectMessage(Device device, Double temperature, Double humidity,
			Boolean detectionVibration, Boolean detectionGasLeak, Boolean detectionAbnormalness) {
		this.device = device;
		this.temperature = temperature;
		this.humidity = humidity;
		this.detectionVibration = detectionVibration;
		this.detectionGasLeak = detectionGasLeak;
		this.detectionAbnormalness = detectionAbnormalness;
	}

	@Override
	public String toString() {
		return "[DEVICE] : " + this.device.getId() + " /[TEMP] : " + this.temperature
				+ " /[HUM] : " + this.humidity + " /[VIBRATION] : " + this.detectionVibration
				+ " /[GASLEAK] : " + this.detectionGasLeak + " /[ABNOMALNESS] : " + this.detectionAbnormalness;
	}
	
	
}
