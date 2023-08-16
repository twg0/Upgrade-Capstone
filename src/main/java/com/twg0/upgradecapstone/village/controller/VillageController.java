package com.twg0.upgradecapstone.village.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timcook.capstone.device.dto.DeviceResponse;
import com.timcook.capstone.file.dto.FileResponse;
import com.timcook.capstone.message.dto.DetectMessageResponse;
import com.timcook.capstone.message.dto.UrgentMessageReponse;
import com.timcook.capstone.message.service.DetectMessageService;
import com.timcook.capstone.message.service.UrgentMessageService;
import com.timcook.capstone.user.dto.UserResponse;
import com.timcook.capstone.village.dto.VillageCreateRequest;
import com.timcook.capstone.village.dto.VillageResponse;
import com.timcook.capstone.village.service.VillageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/villages")
@RequiredArgsConstructor
@Slf4j
public class VillageController {

	private final VillageService villageService;
	private final DetectMessageService detectMessageService;
	private final UrgentMessageService urgentMessageService;
	
	@GetMapping
	public ResponseEntity<List<VillageResponse>> findAll(){
		log.info("마을 전체 리스트 조회");
		return ResponseEntity.ok(villageService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<VillageResponse> create(@Valid @RequestBody VillageCreateRequest villageCreateRequest){
		log.info("마을 생성");
		return ResponseEntity.ok(villageService.create(villageCreateRequest));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VillageResponse> findById(@PathVariable Long id){
		log.info("이름으로 마을 조회");
		return ResponseEntity.ok(villageService.findById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		log.info("마을 삭제");
		villageService.delete(id);
		return ResponseEntity.ok("마을이 삭제되었습니다.");
	}
	
	@GetMapping("/{id}/devices")
	public ResponseEntity<List<DeviceResponse>> findAllDevices(@PathVariable Long id){
		log.info("단말기 리스트 조회");
		return ResponseEntity.ok(villageService.findAllDevices(id));
	}
	@PostMapping("/{id}/admins")
	public ResponseEntity<String> setAdmin(@PathVariable Long id, Long adminId){
		log.info("이장 등록");
		villageService.setAdmin(id, adminId);
		return ResponseEntity.ok("이장이 등록되었습니다.");
	}
	
	@PutMapping("/{id}/admins")
	public ResponseEntity<String> changeAdmin(@PathVariable Long id, Long adminId){
		log.info("이장 변경");
		villageService.setAdmin(id, adminId);
		return ResponseEntity.ok("이장이 변경되었습니다.");
	}
	
	@DeleteMapping("/{id}/admins")
	public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
		log.info("이장 삭제");
		villageService.deleteAdmin(id);
		return ResponseEntity.ok("이장이 삭제되었습니다.");
	}
	
	@GetMapping("/{id}/files")
	public ResponseEntity<List<FileResponse>> getFiles(@PathVariable Long id){
		log.info("방송파일 조회");
		return ResponseEntity.ok(villageService.getFiles(id));
	}
	
	@GetMapping("/{id}/users")
	public ResponseEntity<List<UserResponse>> getUsers(@PathVariable Long id){
		log.info("마을 유저 리스트 조회");
		return ResponseEntity.ok(villageService.getUsers(id));
	}
	
	@GetMapping("/{id}/urgentMessages")
	public ResponseEntity<List<UrgentMessageReponse>> getUrgentMessages(@PathVariable Long id){
		return ResponseEntity.ok(villageService.findAllUrgentMessages(id));
	}
	
	@GetMapping("/{id}/detectMessages")
	public ResponseEntity<List<DetectMessageResponse>> getDetectMessages(@PathVariable Long id){
		return ResponseEntity.ok(villageService.findAllDetectMessages(id));
	}
	
	@GetMapping("/{id}/except/guardians")
	public ResponseEntity<List<UserResponse>> getExceptGaurdians(@PathVariable Long id){
		log.info("마을 유저들(보호자 제외) 조회");
		return ResponseEntity.ok(villageService.getExceptGuardians(id));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<VillageResponse>> search(String words){
		log.info("검색어 = [{}]",words);
		return ResponseEntity.ok(villageService.search(words));
	}
}

