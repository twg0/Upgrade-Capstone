package com.twg0.upgradecapstone.device.repository;

import java.util.List;

import com.timcook.capstone.device.dto.ConfirmResponse;
import com.timcook.capstone.device.dto.DisabledResponse;
import com.timcook.capstone.device.dto.UnconfirmResponse;

public interface CustomDeviceRepository {
	
	List<DisabledResponse> getDisabled(Long deviceId);
	UnconfirmResponse getUnconfirm(Long deviceId);
	List<ConfirmResponse> getconfirm(Long deviceId);
	
}
