package com.twg0.upgradecapstone.notification.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.timcook.capstone.notification.dto.NotificationRequest;
import com.timcook.capstone.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/token")
	public ResponseEntity<String> register(Long userId, String token) {
		notificationService.register(userId, token);

		// 토큰 등록 알림
		notificationService.sendNotification(NotificationRequest.builder()
			.token(notificationService.getToken(userId))
			.title("토큰 등록 확인")
			.body("토큰이 정상적으로 등록되었습니다")
			.build());

		return ResponseEntity.ok("토큰이 등록되었습니다.");
	}

	@PostMapping("/test")
	public ResponseEntity<List<Map<String, String>>> getDetectMessage(String token) {
		log.info("감지 메세지 발송 테스트");

		notificationService.sendNotification(NotificationRequest.builder()
			.token(token)
			.title("지진 감지 알림")
			.body("지진이 감지되었습니다")
			.build());

		Map<String, String> map1 = new HashMap<>();
		map1.put("token", token);
		map1.put("title", "지진 감지 알림");
		map1.put("body", "지진이 감지되었습니다");
		map1.put("token", token);

		notificationService.sendNotification(NotificationRequest.builder()
			.token(token)
			.title("가스 누출 알림")
			.body("가스가 감지되었습니다")
			.build());

		Map<String, String> map2 = new HashMap<>();
		map2.put("token", token);
		map2.put("title", "가스 누출 알림");
		map2.put("body", "가스가 감지되었습니다");
		map2.put("token", token);

		return ResponseEntity.ok(List.of(map1, map2));
	}
}
