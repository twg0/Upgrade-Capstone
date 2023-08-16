package com.twg0.upgradecapstone.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timcook.capstone.file.domain.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
