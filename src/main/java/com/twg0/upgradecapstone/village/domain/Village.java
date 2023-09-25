package com.twg0.upgradecapstone.village.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.BatchSize;

import com.twg0.upgradecapstone.admin.domain.Admin;
import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.file.domain.File;
import com.twg0.upgradecapstone.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class Village {

	@Id
	@GeneratedValue
	@Column(name = "VILLAGE_ID")
	@NotNull
	private Long id;

	@OneToOne(mappedBy = "village", fetch = FetchType.LAZY)
	private Admin admin;

	@Column(length = 20)
	private String nickname;

	@BatchSize(size = 100)
	@OneToMany(mappedBy = "village")
	private List<Device> devices = new ArrayList<>();

	@OneToMany(mappedBy = "village", cascade = CascadeType.ALL)
	private List<File> files = new ArrayList<>();

	@OneToMany
	private List<User> users = new ArrayList<>();

	@Embedded
	private Address address;

	@Embedded
	private Location location;

	@Builder
	public Village(Admin admin, String nickname, List<Device> devices, Address address, Location location) {
		this.admin = admin;
		this.nickname = nickname;
		this.devices = devices;
		this.address = address;
		this.location = location;
	}

	public void addDevice(Device device) {
		this.devices.add(device);
	}

	public void removeDevice(Device device) {
		this.devices.removeIf(d -> d.equals(device));
	}

	public void updateAdmin(Admin admin) {
		if (!Objects.isNull(this.admin)) {
			this.admin.removeVillage();
		}
		this.admin = admin;
	}

	public void addUser(User user) {
		log.info("addUser() : {}", user.getId());
		this.users.add(user);
	}

	public void removeUser(User user) {
		this.users.removeIf(u -> u.equals(user));
	}
}
