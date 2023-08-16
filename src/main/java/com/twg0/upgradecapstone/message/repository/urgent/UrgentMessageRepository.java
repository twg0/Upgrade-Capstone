package com.twg0.upgradecapstone.message.repository.urgent;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.upgradecapstone.message.domain.UrgentMessage;

public interface UrgentMessageRepository extends JpaRepository<UrgentMessage, Long>, CustomUrgentMessageRepository{

}
