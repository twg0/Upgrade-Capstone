package com.twg0.upgradecapstone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

	@GetMapping("/login/success")
	public String success() {
		return "로그인이 성공적으로 완료되었습니다";
	}
	
	@GetMapping("/login/fail")
	public String fail() {
		return "로그인에 실패하였습니다";
	}
	
	@GetMapping("/logout/success")
	public String logout() {
		return "로그아웃이 성공적으로 완료되었습니다.";
	}
}
