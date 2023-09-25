package com.twg0.upgradecapstone.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timcook.capstone.admin.dto.AdminResponse;
import com.timcook.capstone.admin.service.AdminService;
import com.timcook.capstone.file.dto.FileCreateRequest;
import com.timcook.capstone.file.dto.FileResponse;
import com.timcook.capstone.file.service.FileService;
import com.timcook.capstone.user.dto.UserResponse;
import com.timcook.capstone.village.dto.VillageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

	private final AdminService adminService;
	private final FileService fileService;

	@GetMapping
	public ResponseEntity<List<AdminResponse>> findAll() {
		log.info("이장 전체 조회");
		return ResponseEntity.ok(adminService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AdminResponse> findById(@PathVariable Long id) {
		log.info("이장 조회");
		return ResponseEntity.ok(adminService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		log.info("이장 삭제");
		adminService.delete(id);
		return ResponseEntity.ok("유저(이장)이 삭제되었습니다.");
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserResponse> changeToUser(@PathVariable Long id) {
		log.info("이장 유저로 변경");
		return ResponseEntity.ok(adminService.changeToUser(id));
	}

	@PostMapping("/{id}/files")
	public ResponseEntity<String> createFile(@PathVariable Long id, @RequestBody FileCreateRequest fileCreateRequest) {
		log.info("---CREATE FILE---");

		Long VillageId = fileCreateRequest.getVillageId();
		Long fileId = fileService.createAndPublish(id, fileCreateRequest);

		fileService.changeDeviceStatus(fileId, VillageId);
		return ResponseEntity.ok("방송 파일이 등록되었습니다..");
	}

	@GetMapping("/{id}/files")
	public ResponseEntity<List<FileResponse>> getFiles(@PathVariable Long id) {
		log.info("방송파일 조회");
		return ResponseEntity.ok(adminService.getFiles(id));
	}

	@GetMapping("/{id}/villages")
	public ResponseEntity<VillageResponse> getVillage(@PathVariable Long id) {
		log.info("관리중인 마을 조회");
		return ResponseEntity.ok(adminService.getVillage(id));
	}
}
