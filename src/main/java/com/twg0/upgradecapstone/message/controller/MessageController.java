package com.twg0.upgradecapstone.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timcook.capstone.message.dto.DetectMessageResponse;
import com.timcook.capstone.message.dto.UrgentMessageReponse;
import com.timcook.capstone.message.service.DetectMessageService;
import com.timcook.capstone.message.service.UrgentMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

	private final DetectMessageService detectMessageService;
	private final UrgentMessageService urgentMessageService;

	@GetMapping("/detect/{userId}")
	public ResponseEntity<List<DetectMessageResponse>> getDetectMessage(@PathVariable Long userId) {
		log.info("=감지 데이터 찾기=");
		log.info("=입력된 userId : {}=", userId);
		return ResponseEntity.ok(detectMessageService.getMessage(userId));
	}

	@GetMapping("/urgent/{userId}")
	public ResponseEntity<List<UrgentMessageReponse>> getUrgentMessage(@PathVariable Long userId) {
		log.info("=긴급 호출 데이터 리스트 조회=");
		log.info("=입력된 userId : {}=", userId);
		return ResponseEntity.ok(urgentMessageService.getMessagesByUserId(userId));
	}

}
