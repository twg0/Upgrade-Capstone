package com.twg0.upgradecapstone.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.message.domain.UrgentMessage;
import com.timcook.capstone.message.dto.UrgentMessageCreateRequest;
import com.timcook.capstone.message.dto.UrgentMessageReponse;
import com.timcook.capstone.message.repository.urgent.UrgentMessageRepository;
import com.timcook.capstone.message.repository.urgent.UrgentMessageRepositoryImpl;
import com.timcook.capstone.notification.dto.NotificationRequest;
import com.timcook.capstone.notification.service.NotificationService;
import com.timcook.capstone.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class UrgentMessageService {

	private final UrgentMessageRepository urgentMessageRepository;
	private final UrgentMessageRepositoryImpl urgentMessageRepositoryImpl;
	private final NotificationService notificationService;
	
	@Transactional
	public UrgentMessage create(UrgentMessageCreateRequest createRequest) {
		return urgentMessageRepository.save(createRequest.toEntity());
	}

	public void sendToMessage(UrgentMessage urgentMessage) {
		log.info("URGENT_MESSAGE_SERVICFE = {}",urgentMessage.getDevice().getId());
		log.info("URGENT_MESSAGE_SERVICFE = {}",urgentMessage.getDevice().getUser().getUsername());
		User deviceUser = urgentMessage.getDevice().getUser();
		List<User> guardians = deviceUser.getGuardians();
		
		for(User guardian : guardians) {
			NotificationRequest notificationRequest =  NotificationRequest.builder()
								.token(notificationService.getToken(guardian.getId()))
								.title("긴급 호출")
								.body("긴급 상황입니다!")
								.build();
			notificationService.sendNotification(notificationRequest);
		}
	}
	
	
	public List<UrgentMessageReponse> getMessagesByUserId (Long userId){
		return urgentMessageRepositoryImpl.findAllByUserId(userId);
	}
	
}
