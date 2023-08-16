package com.twg0.upgradecapstone.village.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timcook.capstone.village.domain.Village;

public interface VillageRepository extends JpaRepository<Village, Long>, CustomVillageRepository{

	
}
