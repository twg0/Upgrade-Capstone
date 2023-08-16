package com.twg0.upgradecapstone.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.message.domain.DetectMessage;
import com.timcook.capstone.message.dto.DetectMessageCreateRequest;
import com.timcook.capstone.message.dto.DetectMessageResponse;
import com.timcook.capstone.message.repository.detect.DetectMessageRepository;
import com.timcook.capstone.message.repository.detect.DetectMessageRepositoryImpl;
import com.timcook.capstone.notification.dto.NotificationRequest;
import com.timcook.capstone.notification.service.NotificationService;
import com.timcook.capstone.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetectMessageService{

	private final DetectMessageRepository detectMessageRepository;
	private final NotificationService notificationService;
	private final DetectMessageRepositoryImpl detectMessageRepositoryImpl;
	
	@Transactional
	public DetectMessage create(DetectMessageCreateRequest createRequest) {
		return detectMessageRepository.save(createRequest.toEntity());
	}
	
	@Transactional
	public void sendToMessage(DetectMessage detectMessage) {
		log.info("SEND MESSAGE");
		User deviceUser = detectMessage.getDevice().getUser();
		List<User> guardians = deviceUser.getGuardians();
		List<NotificationRequest> notificationRequests = makeNoticiation(detectMessage);
		
		log.info("SET NOTIFICATION");
		
		log.info("GAUR SIZE : {}",guardians.size());
		
		for(User guardian : guardians) {
			log.info("GUAR");
			for(NotificationRequest notification : notificationRequests) {
				notification.setToken(notificationService.getToken(guardian.getId()));
				notificationService.sendNotification(notification);
			}
		}
		log.info("END NOTIFICATION");
	}
	
	private List<NotificationRequest> makeNoticiation(DetectMessage detectMessage) {
		List<NotificationRequest> results = new ArrayList<>();
		
		if(detectMessage.getDetectionVibration()) {
			results.add(new NotificationRequest(null, "지진 감지 알림", "지진이 감지되었습니다"));
		}
		if(detectMessage.getDetectionGasLeak()) {
			results.add(new NotificationRequest(null, "가스 누출 알림", "가스 누출이 감지되었습니다"));
		}
		if(detectMessage.getDetectionAbnormalness()) {
			results.add(new NotificationRequest(null, "이상행동 감지 알림", "이상행동이 감지되었습니다"));
		}
		
		return results;
	}
	
	@Transactional(readOnly = true)
	public List<DetectMessageResponse> getMessage(Long userId) {
		
		List<DetectMessage> detectMessages = detectMessageRepositoryImpl.findAllMessagesByUserId(userId);
		
		return detectMessages.stream()
				.map(m -> DetectMessageResponse.from(m))
				.collect(Collectors.toList());
	}
	
}
