package com.twg0.upgradecapstone.admin.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.file.domain.File;
import com.twg0.upgradecapstone.user.domain.Role;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.village.domain.Village;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends User {
	
	@OneToMany(mappedBy = "admin", orphanRemoval = true)
	private List<File> files = new ArrayList<>();
	
	public Admin(String username, String password, String email, Role role,
			Device device, User ward, Village village, String phoneNumber, String address) {
		super(username, password, email, role, device, ward, village, phoneNumber, address);
	}
	
	public User toUser() {
		return User.builder()
					.username(this.getUsername())
					.password(this.getPassword())
					.email(this.getEmail())
					.role(Role.ROLE_USER)
					.device(this.getDevice())
					.ward(this.getWard())
					.village(this.getVillage())
					.phoneNumber(this.getPhoneNumber())
					.address(this.getAddress())
					.build();
	}
	
	public void removeVillage() {
		if(Optional.ofNullable(this.village).isPresent()) {
			this.village = null;
		}else {
			throw new IllegalArgumentException("등록된 마을이 없습니다.");
		}
	}
	
	public void registerVillage(Village village) {
		if(Objects.isNull(this.village)) {
			this.village=village;
			this.village.updateAdmin(this);
		}else {
			throw new IllegalStateException("이미 관리중인 마을이 존재합니다.");
		}
	}
	
	public void addFile(File file) {
		this.files.add(file);
	}
}
