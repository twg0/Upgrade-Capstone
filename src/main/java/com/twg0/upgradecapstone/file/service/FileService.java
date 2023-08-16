package com.twg0.upgradecapstone.file.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.admin.domain.Admin;
import com.timcook.capstone.admin.repository.AdminRepository;
import com.timcook.capstone.common.mqtt.MqttBuffer;
import com.timcook.capstone.common.mqtt.MqttConfig.OutboundGateWay;
import com.timcook.capstone.common.mqtt.MqttUtils;
import com.timcook.capstone.device.repository.DeviceRepository;
import com.timcook.capstone.device.service.DisabledService;
import com.timcook.capstone.device.service.UnconfirmService;
import com.timcook.capstone.file.domain.File;
import com.timcook.capstone.file.dto.FileCreateRequest;
import com.timcook.capstone.file.repository.FileRepository;
import com.timcook.capstone.message.domain.MessageType;
import com.timcook.capstone.message.dto.BroadcastMessage;
import com.timcook.capstone.village.domain.Village;
import com.timcook.capstone.village.repository.VillageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

	private final int TIME_INTERVAL = 15000; 
	
	private final MqttUtils mqttUtils;
	private final UnconfirmService unconfirmService;
	private final DisabledService disabledService;
	
	private final FileRepository fileRepository;
	private final AdminRepository adminRepository;
	private final VillageRepository villageRepository;
	private final DeviceRepository deviceRepository;
	private final OutboundGateWay outboundGateWay;
	private final EntityManager em;

	
	@Transactional
	public Long createAndPublish(Long adminId, FileCreateRequest fileCreateRequest) {
		Admin admin = adminRepository.findById(adminId)
								.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		
		Village village = villageRepository.findById(fileCreateRequest.getVillageId())
								.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		
		File file = File.builder()
						.admin(admin)
						.title(fileCreateRequest.getTitle())
						.village(village)
						.contents(fileCreateRequest.getContents())
						.build();
		
		admin.addFile(file);
		fileRepository.save(file);
		
		publish(file, village);
		

		village.getDevices()
				.forEach(device -> {
					log.info("==========BUFFER ADD===========");
					MqttBuffer.CONFIRM_BUFFER.add(Pair.of(device.getId(), file.getId()));
					MqttBuffer.RECIEVE_BUFFER.add(Pair.of(device.getId(), file.getId()));
				});
		
		return file.getId();
	}
	
	@Async
	@Transactional
	public void changeDeviceStatus(Long fileId, Long villageId) {
		try {
			log.info("SLEEEEEEEEEEEPPPPP");
			
			Thread.sleep(TIME_INTERVAL);
			
			log.info("AWAKEEEEEEEEEEEEEE");
			
			disabledService.save(getDisabledDevices(fileId));
			
			unconfirmService.save(fileRepository.getById(fileId), getUnconfirmDevices(fileId));
			
		}catch (InterruptedException e) {
			System.err.format("IOEXCEPTION: %s%n",e);
		}
	}
	
	private List<Long> getDisabledDevices(Long fileId) {
		List<Long> disabledDevices = MqttBuffer.RECIEVE_BUFFER.stream()
				.filter(p -> p.getSecond().equals(fileId))
				.map(p -> p.getFirst())
				.collect(Collectors.toList());
		
		MqttBuffer.RECIEVE_BUFFER.removeIf(p -> p.getSecond().equals(fileId));
		
		return disabledDevices;
	}
	
	private List<Long> getUnconfirmDevices(Long fileId) {
		List<Long> unconfirmDevices = MqttBuffer.CONFIRM_BUFFER.stream()
					.filter(p -> p.getSecond().equals(fileId))
					.map(p -> p.getFirst())
					.collect(Collectors.toList());
		
		MqttBuffer.CONFIRM_BUFFER.removeIf(p -> p.getSecond().equals(fileId));
		
		return unconfirmDevices;
	}
	
	private void publish(File file, Village village) {
		log.info("FILE PUBLISH");
		log.info("villageId:{}",village.getId());
		
		BroadcastMessage broadcastMessage = BroadcastMessage.builder()
												.type(MessageType.MASTER)
												.title(file.getTitle())
												.fileId(file.getId().toString())
												.contents(file.getContents())
												.build();

		log.info("[TOPIC] : village/{}",village.getId().toString());
		log.info("[PAYLOAD] : {}",broadcastMessage.toPayload());
		outboundGateWay.sendToMqtt(broadcastMessage.toPayload(), "village/"+village.getId().toString());
		
		log.info("-------PUBLISH BROADCAST-------");
		log.info("[FILE] : {}", broadcastMessage.toPayload());
	}
}
