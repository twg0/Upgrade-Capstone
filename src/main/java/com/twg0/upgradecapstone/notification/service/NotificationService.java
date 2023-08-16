package com.twg0.upgradecapstone.notification.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.twg0.upgradecapstone.common.firebase.FCMService;
import com.twg0.upgradecapstone.notification.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

	private final Map<Long, String> tokenMap = new HashMap<>();
	private final FCMService fcmService;
	
	public void register(Long userId, String token) {
		log.info("==원래 존재하던 토큰 정보==");
		log.info("==userId = {}, token = {}", userId, tokenMap.get(userId));
		tokenMap.put(userId, token);
		log.info("==바뀐 토큰 정보==");
		log.info("==userId = {}, token = {}", userId, tokenMap.get(userId));
	}
	
	public String getToken(Long userId) {
		return tokenMap.get(userId);
	}
	
	public void deleteToken(Long userId) {
		tokenMap.remove(userId);
	}
	
	public void sendNotification(NotificationRequest notificationRequest) {
		try {
			fcmService.sendMessageTo(notificationRequest);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
