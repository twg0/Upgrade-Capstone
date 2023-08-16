package com.twg0.upgradecapstone.user.dto;

import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {

	private String username;
	private String email;
	private String phoneNumber;
	private Device device;
	
	public static UserUpdateRequest from(User user) {
		return new UserUpdateRequest(user.getUsername(), user.getEmail(), user.getPhoneNumber() ,user.getDevice());
	}
}
