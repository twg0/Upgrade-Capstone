package com.twg0.upgradecapstone.device.repository;

import java.util.List;

import com.twg0.upgradecapstone.device.dto.ConfirmResponse;
import com.twg0.upgradecapstone.device.dto.DisabledResponse;
import com.twg0.upgradecapstone.device.dto.UnconfirmResponse;

public interface CustomDeviceRepository {
	
	List<DisabledResponse> getDisabled(Long deviceId);
	UnconfirmResponse getUnconfirm(Long deviceId);
	List<ConfirmResponse> getconfirm(Long deviceId);
	
}
