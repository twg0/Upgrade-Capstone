package com.twg0.upgradecapstone.device.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.device.dto.ConfirmResponse;
import com.timcook.capstone.device.dto.DeviceRegisterUserRequest;
import com.timcook.capstone.device.dto.DeviceRegisterVillageRequest;
import com.timcook.capstone.device.dto.DeviceResponse;
import com.timcook.capstone.device.dto.DisabledResponse;
import com.timcook.capstone.device.dto.UnconfirmResponse;
import com.timcook.capstone.device.repository.DeviceRepository;
import com.timcook.capstone.device.repository.DeviceRepositoryImpl;
import com.timcook.capstone.message.dto.SettingRequestMessage;
import com.timcook.capstone.message.dto.SettingResponseMessage;
import com.timcook.capstone.user.domain.User;
import com.timcook.capstone.user.repository.UserRepository;
import com.timcook.capstone.village.domain.Village;
import com.timcook.capstone.village.repository.VillageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DeviceService {

	private final DeviceRepository deviceRepository;
	private final UserRepository userRepository;
	private final VillageRepository villageRepository;
	private final DeviceRepositoryImpl deviceRepositoryImpl;
	
	public Device findDeviceById (Long id) {
		return deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 단말기가 존재하지 않습니다."));
	}
	
	public List<DeviceResponse> findAll(){
		return deviceRepository.findAll().stream()
						.map(device -> DeviceResponse.from(device))
						.collect(Collectors.toList());
	}
	
	public DeviceResponse findById(Long id) {
		Device device = deviceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 단말기가 존재하지 않습니다."));
		return DeviceResponse.from(device);
	}
	
	@Transactional
	public Long create() {
		Device device = Device.builder().build();
		deviceRepository.save(device);
		return device.getId();
	}
	
	@Transactional
	public void delete(Long id) {
		Device device = deviceRepository.findById(id)
								.orElseThrow(() -> new IllegalArgumentException("해당 단말기가 존재하지 않습니다."));
		
		if(Optional.ofNullable(device.getUser()).isPresent()) {
			device.getUser().removeDevice();
		}
		
		if(Optional.ofNullable(device.getVillage()).isPresent()) {
			device.getVillage().removeDevice(device);
		}
		
		deviceRepository.delete(device);
	}
	
	@Transactional
	public DeviceResponse registerUser(Long id ,DeviceRegisterUserRequest deviceRegisterUserRequest) {
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 단말기가 존재하지 않습니다."));
		User user = userRepository.findById(deviceRegisterUserRequest.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		device.registerUser(user);
		user.registerDevice(device);
		
		return DeviceResponse.from(device);
	}
	
	@Transactional
	public DeviceResponse registerVillage(Long id ,DeviceRegisterVillageRequest deviceRegisterVillageRequest) {
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 단말기가 존재하지 않습니다."));
		Village village = villageRepository.findById(deviceRegisterVillageRequest.getVillageId())
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));

		device.registerVillage(village);
		
		return DeviceResponse.from(device);
	}
	
	@Transactional
	public SettingResponseMessage deviceConnectUser(SettingRequestMessage settingRequestMessage) {
		log.info("PHONE NUMBER = {}",settingRequestMessage.getPhoneNumber());
		
		Optional<User> user = userRepository.findByPhoneNumber(settingRequestMessage.getPhoneNumber());
		Optional<Device> device = deviceRepository.findById(settingRequestMessage.getDeviceId());
		
		if(user.isEmpty() || user.get().getDevice() != null || device.get().getUser() != null || user.get().getVillage() == null) {
			return null;
		}
		
		/*
		 * uesr <-> device 연결 성공
		 */
		user.get().registerDevice(device.get());
		return SettingResponseMessage.builder() 
								.deviceId(device.get().getId())
								.username(user.get().getUsername())
								.villageId(user.get().getVillage().getId())
								.build(); 
	}
	
	public List<DisabledResponse> getDisabled(Long deviceId){
		return deviceRepositoryImpl.getDisabled(deviceId);
	}

	public UnconfirmResponse getUncofirm(Long deviceId){
		return deviceRepositoryImpl.getUnconfirm(deviceId);
	}
	
	public List<ConfirmResponse> getConfirm(Long deviceId){
		return deviceRepositoryImpl.getconfirm(deviceId);
	}
}
