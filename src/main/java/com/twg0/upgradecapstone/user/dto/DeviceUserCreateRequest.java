package com.twg0.upgradecapstone.user.dto;

import java.util.UUID;

import com.twg0.upgradecapstone.user.domain.Role;
import com.twg0.upgradecapstone.user.domain.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUserCreateRequest {
	@NotBlank
	@Size(max = 10, message = "이름은 10자를 넘길 수 없습니다.")
	private String username;
	@NotBlank
	@Size(max = 11, message = "핸드폰 번호는 11자를 넘길 수 없습니다.")
	private String phoneNumber;
	@NotBlank
	private String address;

	public User toEntity() {
		return User.builder()
			.username(username)
			.phoneNumber(phoneNumber)
			.email(UUID.randomUUID().toString().substring(0, 29))
			.address(address)
			.role(Role.ROLE_USER)
			.build();
	}
}
