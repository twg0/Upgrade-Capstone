package com.twg0.upgradecapstone.user.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginVO {
	
	@Max(value = 30)
	@NotNull(message = "EMAIL 값을 입력해주세요")
	@Email
	private String email;
	
	@NotNull(message = "PASSWORD 값을 입력해주세요")
	private String password;
	
}
