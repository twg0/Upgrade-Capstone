package com.twg0.upgradecapstone.common.mqtt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timcook.capstone.common.mqtt.MqttConfig.OutboundGateWay;
import com.timcook.capstone.device.service.DeviceService;
import com.timcook.capstone.message.domain.DetectMessage;
import com.timcook.capstone.message.domain.MessageFormat;
import com.timcook.capstone.message.domain.MessageType;
import com.timcook.capstone.message.domain.UrgentMessage;
import com.timcook.capstone.message.dto.DetectMessageCreateRequest;
import com.timcook.capstone.message.dto.MessageCreateRequsetInterface;
import com.timcook.capstone.message.dto.ReplyMessageCreateRequest;
import com.timcook.capstone.message.dto.SettingRequestMessage;
import com.timcook.capstone.message.dto.SettingResponseMessage;
import com.timcook.capstone.message.dto.UrgentMessageCreateRequest;
import com.timcook.capstone.message.factory.MessageCreateRequestFactory;
import com.timcook.capstone.message.service.DetectMessageService;
import com.timcook.capstone.message.service.ReplyMessageService;
import com.timcook.capstone.message.service.UrgentMessageService;
import com.timcook.capstone.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MqttUtils {

	private static final String DEVICE_TOPIC_FILTER = "topic/device/"; 
	private static final String SPLIT_REGEX = "/";
	
	private final MessageCreateRequestFactory messageCreateRequestFactory = new MessageCreateRequestFactory();
	
	private final DeviceService deviceService;
	private final ReplyMessageService replyMessageService;
	private final DetectMessageService detectMessageService;
	private final UrgentMessageService urgentMessageService;
	private final NotificationService notificationService;
	private final OutboundGateWay outboundGateWay;
	
	public void payloadToMessage(String payload) {
		log.info("=============MQTT UTILS=============");
		log.info("PAYLOAD : {}", payload);
		
		MessageCreateRequsetInterface createRequest 
				= messageCreateRequestFactory.create(getMessageType(payload), parsePayload(payload));
		
		MessageType messageType = getMessageType(payload);
		
		if(messageType.equals(MessageType.URGENT)) {
			
			UrgentMessageCreateRequest urgentMessageCreateRequest = (UrgentMessageCreateRequest)createRequest;
			urgentMessageCreateRequest.setDevice(deviceService.findDeviceById(getDeviceId(payload)));
			
			UrgentMessage urgentMessage = urgentMessageService.create(urgentMessageCreateRequest);
			urgentMessageService.sendToMessage(urgentMessage);
			
		} else if(messageType.equals(MessageType.DETECT)){
			
			DetectMessageCreateRequest detectMessageCreateRequest = (DetectMessageCreateRequest) createRequest;
			detectMessageCreateRequest.setDevice(deviceService.findDeviceById(getDeviceId(payload)));
			
			DetectMessage detectMessage = detectMessageService.create(detectMessageCreateRequest );
			detectMessageService.sendToMessage(detectMessage);
			
		} else if(messageType.equals(MessageType.REPLY)){
			
			ReplyMessageCreateRequest replyMessageCreateRequest = (ReplyMessageCreateRequest) createRequest;
			replyMessageCreateRequest.setDevice(deviceService.findDeviceById(getDeviceId(payload)));
			replyMessageService.changeStatus(replyMessageCreateRequest);
			
		} else if(messageType.equals(MessageType.SETTING)){
				
			SettingRequestMessage settingMessage = (SettingRequestMessage) createRequest;
			responseSettingMessage(settingMessage);
			
		}
	}
	
	private void responseSettingMessage(SettingRequestMessage settingRequestMessage) {
		SettingResponseMessage settingResponseMessage = deviceService.deviceConnectUser(settingRequestMessage);
		
//		log.info("SETTING MESSAGE DEVICE ID = {}",settingResponseMessage.getDeviceId());
		
		if(settingResponseMessage == null) {
			log.info("RESPONSE MESSAGE NULL");
			outboundGateWay.sendToMqtt(SettingResponseMessage.connectFailPayload(), 
										DEVICE_TOPIC_FILTER + settingRequestMessage.getDeviceId().toString());
		}else {
			log.info("RESPONSE MESSAGE SUCCESS");
			outboundGateWay.sendToMqtt(SettingResponseMessage.connectSuccessPayload(settingResponseMessage),
										DEVICE_TOPIC_FILTER + settingResponseMessage.getDeviceId().toString());
		}
	}
	
	private MessageType getMessageType(String payload) {
		log.info("PAYLOAD_MESSAGE_TYPE : {}",parsePayload(payload).get(MessageFormat.MESSAGE_TYPE.getIndex()));
		
		String messageType = parsePayload(payload).get(MessageFormat.MESSAGE_TYPE.getIndex()); 
		
		if(messageType.equals(MessageType.URGENT.name())) {
			return MessageType.URGENT;
		}else if(messageType.equals(MessageType.DETECT.name())){
			return MessageType.DETECT;
		}else if(messageType.equals(MessageType.REPLY.name())){
			return MessageType.REPLY;
		}else if(messageType.equals(MessageType.SETTING.name())) {
			return MessageType.SETTING;
		}else {
			return null;
		}
	}

	private Long getDeviceId(String payload) {
		return Long.valueOf(parsePayload(payload).get(MessageFormat.DEVICE_ID.getIndex()));
	}
	
	private List<String> parsePayload(String payload){
		return new ArrayList<>(Arrays.asList(payload.split(SPLIT_REGEX)));
	}
	
}
