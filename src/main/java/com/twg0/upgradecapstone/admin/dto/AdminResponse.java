package com.twg0.upgradecapstone.admin.dto;

import com.twg0.upgradecapstone.admin.domain.Admin;
import com.twg0.upgradecapstone.user.domain.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
	
	@NotNull
	private Long id;
	
	private String username;
	private String email;
	private String phoneNumber;
	private String address;
	private Role role;
	
	public static AdminResponse from(Admin admin) {
		return AdminResponse.builder()
				.id(admin.getId())
				.username(admin.getUsername())
				.email(admin.getEmail())
				.phoneNumber(admin.getPhoneNumber())
				.address(admin.getAddress())
				.role(admin.getRole())
				.build();
	}
}
