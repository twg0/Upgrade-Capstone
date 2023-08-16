package com.twg0.upgradecapstone.village.repository;

import java.util.List;

import com.timcook.capstone.device.dto.DeviceResponse;
import com.timcook.capstone.file.dto.FileResponse;
import com.timcook.capstone.message.dto.DetectMessageResponse;
import com.timcook.capstone.message.dto.UrgentMessageReponse;
import com.timcook.capstone.user.dto.UserResponse;
import com.timcook.capstone.village.dto.VillageResponse;

public interface CustomVillageRepository {
		
	List<DeviceResponse> findAllDevices(Long id);
	List<FileResponse> findAllFiles(Long id);
	List<UserResponse> findAllUsers(Long id);
	List<UrgentMessageReponse> findAllUrgentMessages(Long id);
	List<DetectMessageResponse> findAllDetectMessages(Long id);
	List<VillageResponse> searchBy(String findStr);
	
}
