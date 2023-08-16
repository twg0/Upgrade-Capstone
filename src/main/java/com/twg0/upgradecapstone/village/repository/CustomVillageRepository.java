package com.twg0.upgradecapstone.village.repository;

import java.util.List;

import com.twg0.upgradecapstone.device.dto.DeviceResponse;
import com.twg0.upgradecapstone.file.dto.FileResponse;
import com.twg0.upgradecapstone.message.dto.DetectMessageResponse;
import com.twg0.upgradecapstone.message.dto.UrgentMessageReponse;
import com.twg0.upgradecapstone.user.dto.UserResponse;
import com.twg0.upgradecapstone.village.dto.VillageResponse;

public interface CustomVillageRepository {
		
	List<DeviceResponse> findAllDevices(Long id);
	List<FileResponse> findAllFiles(Long id);
	List<UserResponse> findAllUsers(Long id);
	List<UrgentMessageReponse> findAllUrgentMessages(Long id);
	List<DetectMessageResponse> findAllDetectMessages(Long id);
	List<VillageResponse> searchBy(String findStr);
	
}
