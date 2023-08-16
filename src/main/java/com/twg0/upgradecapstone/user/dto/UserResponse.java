package com.twg0.upgradecapstone.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.timcook.capstone.user.domain.Role;
import com.timcook.capstone.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {
	
	private Long id; 
	private String username;
	private String email;
	private Role role;
	private String phoneNumber;
	private String address;
	
	@Builder
	@QueryProjection
	public UserResponse (Long id, String username, String email, Role role, String phoneNumber, String address) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
	public static UserResponse from(User user) {
		if(user == null) return null;
		return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), 
				user.getRole(), user.getPhoneNumber(), user.getAddress());
	}

	
}
