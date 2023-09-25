package com.twg0.upgradecapstone.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {

	@NotNull
	@Size(max = 10, message = "이름은 10자를 넘길 수 없습니다.")
	private String username;
	@NotNull
	@Size(max = 11, message = "핸드폰 번호는 11자를 넘길 수 없습니다.")
	private String phoneNumber;
	@NotNull
	private String address;
}
