package com.twg0.upgradecapstone.message.factory;

import java.util.List;

import com.twg0.upgradecapstone.message.domain.MessageType;
import com.twg0.upgradecapstone.message.dto.MessageCreateRequsetInterface;

public abstract class AbstractMessageCreateRequestFactory {
	abstract MessageCreateRequsetInterface create(MessageType messageType, List<String> payload);
}
