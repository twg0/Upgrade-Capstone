package com.twg0.upgradecapstone.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twg0.upgradecapstone.device.domain.Disabled;

public interface DisabledRepository extends JpaRepository<Disabled, Long> {

}
