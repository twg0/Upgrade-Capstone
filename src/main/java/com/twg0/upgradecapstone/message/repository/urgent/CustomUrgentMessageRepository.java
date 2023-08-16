package com.twg0.upgradecapstone.message.repository.urgent;

import java.util.List;

import com.twg0.upgradecapstone.message.dto.UrgentMessageReponse;

public interface CustomUrgentMessageRepository {

	List<UrgentMessageReponse> findAllByUserId(Long userId);
	
}
