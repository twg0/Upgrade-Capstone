package com.twg0.upgradecapstone.common.mqtt;

import java.util.HashSet;

import org.springframework.data.util.Pair;

public class MqttBuffer {
	public static final HashSet<Pair<Long, Long>> RECIEVE_BUFFER = new HashSet<>();
	public static final HashSet<Pair<Long, Long>> CONFIRM_BUFFER = new HashSet<>();
}
