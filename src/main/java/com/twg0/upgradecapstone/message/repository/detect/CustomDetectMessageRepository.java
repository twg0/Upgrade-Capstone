package com.twg0.upgradecapstone.message.repository.detect;

import java.util.List;

import com.timcook.capstone.message.domain.DetectMessage;

public interface CustomDetectMessageRepository {

	List<DetectMessage> findAllMessagesByUserId(Long userId);
	
}
