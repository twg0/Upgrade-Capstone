package com.twg0.upgradecapstone.device.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.twg0.upgradecapstone.message.domain.DetectMessage;
import com.twg0.upgradecapstone.message.domain.UrgentMessage;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.village.domain.Village;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@NoArgsConstructor
@Slf4j
public class Device {

	@Id
	@GeneratedValue
	@Column(name = "DEVICE_ID")
	@NotNull
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VILLAGE_ID")
	private Village village;

	@OneToOne(mappedBy = "device", fetch = FetchType.LAZY)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;

	@OneToMany(mappedBy = "device", orphanRemoval = true)
	private List<DetectMessage> detectMessages = new ArrayList<>();

	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<UrgentMessage> urgentMessages = new ArrayList<>();

	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Unconfirm> unconfirmInfos = new ArrayList<>();

	@OneToMany(mappedBy = "device", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Disabled> disabledInfos = new ArrayList<>();

	@Builder
	public Device(Village village, User user, Status status) {
		this.village = village;
		this.user = user;
		this.status = Status.DISABLE;
	}

	public void addDetectMessage(DetectMessage message) {
		this.detectMessages.add(message);
	}

	public void addUnconfirmCount(Unconfirm unconfirm) {
		this.unconfirmInfos.add(unconfirm);
	}

	public void addDisabledCount(Disabled disabled) {
		this.disabledInfos.add(disabled);
	}

	public void addUrgentMessage(UrgentMessage urgentMessage) {
		this.urgentMessages.add(urgentMessage);
	}

	public void registerUser(User user) {
		if (Objects.isNull(this.user)) {
			this.user = user;
		}
	}

	public void removeUser() {
		if (Optional.ofNullable(this.user).isPresent()) {
			this.user = null;
		} else {
			throw new IllegalArgumentException("사용중인 유저가 없습니다.");
		}
	}

	public void removeVillage() {
		if (Optional.ofNullable(this.village).isPresent()) {
			this.village = null;
		} else {
			throw new IllegalArgumentException("등록된 마을이 없습니다.");
		}
	}

	public void registerVillage(Village village) {
		if (Objects.isNull(this.village)) {
			this.village = village;
			this.village.addDevice(this);
		}
	}

	public void changeInfo(User user, Village village) {
		changeUser(user);
		changeVillage(village);
	}

	public void changeStatus(Status status) {
		log.info("--DEVICE CHANGES STATUS--");
		log.info("-- {} -> {} --", this.status, status);
		this.status = status;
	}

	private void changeUser(User user) {
		if (Objects.isNull(this.user)) {
			this.user = user;
			this.user.changeDevcie(this);
		}
	}

	private void changeVillage(Village village) {
		if (Objects.isNull(this.village)) {
			this.village = village;
			this.village.addDevice(this);
		}
	}

}

