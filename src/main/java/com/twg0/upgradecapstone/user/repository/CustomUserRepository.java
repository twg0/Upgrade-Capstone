package com.twg0.upgradecapstone.user.repository;

import java.util.List;

import com.twg0.upgradecapstone.user.dto.UserResponse;

public interface CustomUserRepository {

	
	List<UserResponse> searchBy(Long villagId, String username);
	
}
