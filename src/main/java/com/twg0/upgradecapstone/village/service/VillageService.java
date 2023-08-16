package com.twg0.upgradecapstone.village.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timcook.capstone.admin.domain.Admin;
import com.timcook.capstone.admin.repository.AdminRepository;
import com.timcook.capstone.device.dto.DeviceResponse;
import com.timcook.capstone.file.dto.FileResponse;
import com.timcook.capstone.message.dto.DetectMessageResponse;
import com.timcook.capstone.message.dto.UrgentMessageReponse;
import com.timcook.capstone.user.domain.User;
import com.timcook.capstone.user.dto.UserResponse;
import com.timcook.capstone.village.domain.Village;
import com.timcook.capstone.village.dto.VillageCreateRequest;
import com.timcook.capstone.village.dto.VillageResponse;
import com.timcook.capstone.village.repository.VillageRepository;
import com.timcook.capstone.village.repository.VillageRepositoryImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VillageService {

	private final VillageRepository villageRepository;
	private final VillageRepositoryImpl villageRepositoryImpl;
	private final AdminRepository adminRepository;

	public List<VillageResponse> findAll(){
		return villageRepository.findAll().stream()
						.map(village -> VillageResponse.from(village))
						.collect(Collectors.toList());
	}
	
	@Transactional
	public VillageResponse create(VillageCreateRequest villageCreateRequest) {
		Village village = villageCreateRequest.toEntity();
		villageRepository.save(village);
		return VillageResponse.from(village);
	}
	
	public VillageResponse findById(Long id) {
		Village village = villageRepository.findById(id)
							.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		return VillageResponse.from(village); 
	}

	@Transactional
	public void delete(Long id) {
		Village village = villageRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		
		if(Optional.ofNullable(village.getUsers()).isPresent()) {
			village.getUsers().forEach(u ->{
				u.removeVillage();
				});
		}

		if(Optional.ofNullable(village.getAdmin()).isPresent()) {
			village.getAdmin().removeVillage();
		}
		
		if(Optional.ofNullable(village.getDevices()).isPresent()) {
			village.getDevices().forEach(d ->{
				d.removeVillage();
				});
		}
		
		villageRepository.delete(village);
	}
	
	public List<DeviceResponse> findAllDevices(Long id){
		return villageRepositoryImpl.findAllDevices(id);
	}
	
	public List<UrgentMessageReponse> findAllUrgentMessages(Long id){
		return villageRepositoryImpl.findAllUrgentMessages(id);
	}
	
	public List<DetectMessageResponse> findAllDetectMessages(Long id){
		return villageRepositoryImpl.findAllDetectMessages(id);
	}
	
	@Transactional
	public void setAdmin(Long villageId, Long adminId) {
		Village village = villageRepository.findById(villageId)
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new IllegalArgumentException("해당 이장이 존재하지 않습니다."));
		
		admin.registerVillage(village);
	}
	
	@Transactional
	public void changeAdmin(Long villageId, Long adminId) {
		Village village = villageRepository.findById(villageId)
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		
		Admin admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new IllegalArgumentException("해당 이장이 존재하지 않습니다."));
		
		admin.registerVillage(village);
	}
	
	@Transactional
	public void deleteAdmin(Long id) {
		Village village = villageRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));

		village.updateAdmin(null);
	}
	
	public List<UserResponse> getExceptGuardians(Long id){
		Village findVillage = villageRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));
		
		List<User> users = findVillage.getUsers();
		
		return users.stream()
				.filter(u -> Objects.isNull(u.getWard()))
				.map(u -> UserResponse.from(u))
				.collect(Collectors.toList());
	}
	
	public List<FileResponse> getFiles(Long id){
		return villageRepositoryImpl.findAllFiles(id);
	}
	
	public List<UserResponse> getUsers(Long id){
		return villageRepositoryImpl.findAllUsers(id);
	}
	
	public List<VillageResponse> search(String words){
		return villageRepositoryImpl.searchBy(words);
	}
}
