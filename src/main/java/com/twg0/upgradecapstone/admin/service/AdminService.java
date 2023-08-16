package com.twg0.upgradecapstone.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.upgradecapstone.admin.domain.Admin;
import com.twg0.upgradecapstone.admin.dto.AdminResponse;
import com.twg0.upgradecapstone.admin.repository.AdminRepository;
import com.twg0.upgradecapstone.admin.repository.AdminRepositoryImpl;
import com.twg0.upgradecapstone.file.dto.FileResponse;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.user.dto.UserResponse;
import com.twg0.upgradecapstone.user.repository.UserRepository;
import com.twg0.upgradecapstone.village.dto.VillageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AdminService {
	
	private final AdminRepository adminRepository;
	private final UserRepository userRepository;
	private final AdminRepositoryImpl adminRepositoryImpl;
	
	public List<AdminResponse> findAll(){
		return adminRepository.findAll().stream()
				.map(admin -> AdminResponse.from(admin))
				.collect(Collectors.toList());
	}
	
	public AdminResponse findById(Long id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		return AdminResponse.from(admin);
	}
	
	@Transactional
	public void delete(Long id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		
		if(Optional.ofNullable(admin.getDevice()).isPresent()) {
			admin.getDevice().removeUser();
		}
		
		if(Optional.ofNullable(admin.getVillage()).isPresent()) {
			admin.getVillage().removeUser(admin);
		}
		
		if(Optional.ofNullable(admin.getGuardians()).isPresent()) {
			admin.removeGaurdian();
		}

		if(Optional.ofNullable(admin.getWard()).isPresent()) {
			admin.removeWard();
		}
		
		adminRepository.delete(admin);
	}
	
	@Transactional
	public UserResponse changeToUser(Long id) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		
		User user = admin.toUser();
		log.info("ADMIN TO USER = {}", user.getId());
		
		userRepository.save(user);
		adminRepository.delete(admin);
		
		return UserResponse.from(user);
	}
	
	public List<FileResponse> getFiles(Long id){
		return adminRepositoryImpl.findAllFiles(id);
	}
			
	
	public VillageResponse getVillage(Long id) {
		Admin findAdmin =  adminRepository.findById(id)
								.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		
		return VillageResponse.from(findAdmin.getVillage());
	}
}
