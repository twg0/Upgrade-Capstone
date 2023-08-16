package com.twg0.upgradecapstone.admin.repository;

import java.util.List;

import com.twg0.upgradecapstone.file.dto.FileResponse;

public interface CustomAdminRepository {
	List<FileResponse> findAllFiles(Long id);
}
