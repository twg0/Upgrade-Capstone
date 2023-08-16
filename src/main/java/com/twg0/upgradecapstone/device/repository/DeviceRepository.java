package com.twg0.upgradecapstone.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.upgradecapstone.device.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>, CustomDeviceRepository{

	
}
