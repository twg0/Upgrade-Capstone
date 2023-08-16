package com.twg0.upgradecapstone.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.device.domain.Unconfirm;
import com.twg0.upgradecapstone.file.domain.File;

public interface UnconfirmRepository extends JpaRepository<Unconfirm, Long> {

	Unconfirm findByFileAndDevice(File file, Device device);
}
