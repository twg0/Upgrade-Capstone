package com.twg0.upgradecapstone.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.device.domain.Unconfirm;
import com.timcook.capstone.file.domain.File;

public interface UnconfirmRepository extends JpaRepository<Unconfirm, Long>{

	Unconfirm findByFileAndDevice(File file, Device device);
}
