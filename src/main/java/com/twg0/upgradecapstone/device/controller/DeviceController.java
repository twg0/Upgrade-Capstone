
package com.twg0.upgradecapstone.device.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timcook.capstone.device.dto.ConfirmResponse;
import com.timcook.capstone.device.dto.DeviceRegisterUserRequest;
import com.timcook.capstone.device.dto.DeviceRegisterVillageRequest;
import com.timcook.capstone.device.dto.DeviceResponse;
import com.timcook.capstone.device.dto.DisabledResponse;
import com.timcook.capstone.device.dto.UnconfirmResponse;
import com.timcook.capstone.device.service.DeviceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

	private final DeviceService deviceService;
	
	@GetMapping
	public ResponseEntity<List<DeviceResponse>> findAll(){
		return ResponseEntity.ok(deviceService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Long>> create() {
		Map<String, Long> result = new HashMap<>();
		Long deviceId = deviceService.create();
		result.put("device_id", deviceId);
		
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		deviceService.delete(id);
		return ResponseEntity.ok("단말기가 삭제되었습니다.");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeviceResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(deviceService.findById(id));
	}
	
	
	@PostMapping("/{id}/users")
	public ResponseEntity<DeviceResponse> registerUser(@Validated @RequestBody DeviceRegisterUserRequest deviceRegisterUserRequest,
			@PathVariable Long id) {
		return ResponseEntity.ok(deviceService.registerUser(id, deviceRegisterUserRequest));
	}
	
	@PostMapping("/{id}/villages")
	public ResponseEntity<DeviceResponse> registerVillage(@Validated @RequestBody DeviceRegisterVillageRequest deviceRegisterVillageRequest,
			@PathVariable Long id) {
		return ResponseEntity.ok(deviceService.registerVillage(id, deviceRegisterVillageRequest));
	}
	
	@GetMapping("/{id}/disabled")
	public ResponseEntity<List<DisabledResponse>> getDisabled(@PathVariable Long id){
		return ResponseEntity.ok(deviceService.getDisabled(id));
	}
	
	@GetMapping("/{id}/unconfirm")
	public ResponseEntity<UnconfirmResponse> getUnconfirm(@PathVariable Long id){
		return ResponseEntity.ok(deviceService.getUncofirm(id));
	}
	
	@GetMapping("/{id}/confirm")
	public ResponseEntity<List<ConfirmResponse>> getconfirm(@PathVariable Long id){
		return ResponseEntity.ok(deviceService.getConfirm(id));
	}
}
