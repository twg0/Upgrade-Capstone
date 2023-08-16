package com.twg0.upgradecapstone.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.timcook.capstone.admin.domain.Admin;
import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.user.dto.UserCreateRequest;
import com.timcook.capstone.village.domain.Village;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User{
	
	@Id @GeneratedValue
	@Column(name = "USER_ID")
	private Long id;
	
	@Column(length = 10, nullable = false)
	private String username;
	
	private String password;
	
	@Column(length = 11)
	private String phoneNumber;
	
	@Column(length = 30, nullable = false, unique = true)
	private String email;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@OneToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "DEVICE_ID")
	private Device device;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WARD_USER_ID")
	private User ward;
	
	@OneToMany(mappedBy = "ward", fetch = FetchType.EAGER)
	private List<User> guardians = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VILLAGE_ID")
	protected Village village;
	
	private String address;
	
	@Builder
	public User(String username, String password, String email, Role role, 
			Device device, User ward, Village village, String phoneNumber, String address) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.device = device;
		this.ward = ward;
		this.village = village;
		this.phoneNumber = phoneNumber;
		this.address = address;		
	}
	
	public void registerInformation(UserCreateRequest userCreateRequest) {
		this.username = userCreateRequest.getUsername();
		this.phoneNumber = userCreateRequest.getPhoneNumber();
		this.address = userCreateRequest.getAddress();
	}
	
	public void changeDevcie(Device device) {
		if(!Objects.isNull(this.device)) {
			this.device = device;
		}
	}
	
	public void addGaurdian(User guardian) {
		guardian.registerWard(this);
		this.guardians.add(guardian);
	}

	public void removeGaurdian() {
		if(Optional.ofNullable(this.guardians).isPresent()) {
			this.guardians.forEach(g -> g.removeWard());
			this.guardians = null;
		}
	}
	
	public Admin toAdmin() {
		return new Admin(this.username, this.password, this.email, Role.ROLE_ADMIN,
				this.device, this.ward, this.village, this.phoneNumber, this.address);
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
			this.village.addUser(this);
		}else {
			throw new IllegalStateException("이미 등록중인 마을이 존재합니다.");
		}
	}
	
	public void registerDevice(Device device) {
		if(Objects.isNull(this.device)) {
			this.device=device;
		}else {
			throw new IllegalStateException("이미 사용중인 단말기가 존재합니다.");
		}
	}
	
	public void removeDevice() {
		if(Optional.ofNullable(this.device).isPresent()) {
			this.device = null;
		}else {
			throw new IllegalArgumentException("사용중인 유저가 없습니다.");
		}
	}
	
	private void registerWard(User ward) {
		this.ward = ward;
	}
	
	public void removeWard() {
		if(Optional.ofNullable(this.ward).isPresent()) {
			this.ward = null;
		}
	}

}
