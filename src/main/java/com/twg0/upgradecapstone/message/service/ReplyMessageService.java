package com.twg0.upgradecapstone.message.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.common.mqtt.MqttBuffer;
import com.timcook.capstone.device.domain.Confirm;
import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.device.domain.Unconfirm;
import com.timcook.capstone.device.repository.ConfirmRepository;
import com.timcook.capstone.device.repository.DeviceRepository;
import com.timcook.capstone.device.repository.UnconfirmRepository;
import com.timcook.capstone.file.domain.File;
import com.timcook.capstone.file.repository.FileRepository;
import com.timcook.capstone.message.dto.ReplyMessageCreateRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyMessageService {

	private static final int RECEPTION = 0;
	private static final int CONFIRM = 1;
	
	private final DeviceRepository deviceRepository;
	private final FileRepository fileRepository;
	private final ConfirmRepository confirmRepository;
	private final UnconfirmRepository unconfirmRepository;
	
	@Transactional
	public void changeStatus(ReplyMessageCreateRequest replyMessageCreateRequest) {
		log.info("-----REPLY SERVICE-----");
		log.info("DEVICE ID : {}",replyMessageCreateRequest.getDevice().getId());
		
		Device device = deviceRepository.getById(replyMessageCreateRequest.getDevice().getId());
		File file = fileRepository.getById(Long.valueOf(replyMessageCreateRequest.getFileId()));
		
		Long fileId = file.getId();
		Long deviceId = device.getId();

		log.info("===GET KIND OF REPLY, {}===", replyMessageCreateRequest.getKindOfReply());
		
		if(replyMessageCreateRequest.getKindOfReply() == CONFIRM) {
			if(!MqttBuffer.CONFIRM_BUFFER.contains(Pair.of(deviceId, fileId))) {
				log.info("==응답 시간 초과 후 응답=> 미확인 방송 확인 처리==");
				log.info("==CONFIRM ADD==");
				confirmRepository.save(Confirm.builder()
												.file(file)
												.device(device)
												.build());
				
				Unconfirm unconfirm = unconfirmRepository.findByFileAndDevice(file, device);
				unconfirmRepository.delete(unconfirm);
			}
			
			MqttBuffer.CONFIRM_BUFFER.remove(Pair.of(deviceId, fileId));
		}else if(replyMessageCreateRequest.getKindOfReply() == RECEPTION) {
			log.info("==BUFFER REMOVE==");
			MqttBuffer.RECIEVE_BUFFER.remove(Pair.of(deviceId, fileId));
		}
		
		log.info("device status : {}", device.getStatus().name());
	}
	
}
