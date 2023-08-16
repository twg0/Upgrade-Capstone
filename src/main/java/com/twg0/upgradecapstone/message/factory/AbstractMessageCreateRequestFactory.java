package com.twg0.upgradecapstone.message.factory;

import java.util.List;

import com.timcook.capstone.message.domain.MessageType;
import com.timcook.capstone.message.dto.MessageCreateRequsetInterface;

public abstract class AbstractMessageCreateRequestFactory {
	abstract MessageCreateRequsetInterface create(MessageType messageType, List<String> payload);
}
