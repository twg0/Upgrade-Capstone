package com.twg0.upgradecapstone.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.twg0.upgradecapstone.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository{
	
	Optional<User> findByEmail(String email);
	
	@Query("select u from User u where u.phoneNumber = :number")
	Optional<User> findByPhoneNumber(@Param("number") String phoneNumber);
	
}
