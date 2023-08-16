package com.twg0.upgradecapstone.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timcook.capstone.device.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>, CustomDeviceRepository{

	
}
