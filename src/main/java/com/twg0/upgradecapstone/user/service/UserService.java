package com.twg0.upgradecapstone.user.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.twg0.upgradecapstone.admin.domain.Admin;
import com.twg0.upgradecapstone.admin.dto.AdminResponse;
import com.twg0.upgradecapstone.admin.repository.AdminRepository;
import com.twg0.upgradecapstone.device.dto.DeviceResponse;
import com.twg0.upgradecapstone.user.domain.Role;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.user.dto.DeviceUserCreateRequest;
import com.twg0.upgradecapstone.user.dto.UserCreateRequest;
import com.twg0.upgradecapstone.user.dto.UserResponse;
import com.twg0.upgradecapstone.user.repository.UserRepository;
import com.twg0.upgradecapstone.user.repository.UserRepositoryImpl;
import com.twg0.upgradecapstone.village.domain.Village;
import com.twg0.upgradecapstone.village.dto.VillageResponse;
import com.twg0.upgradecapstone.village.repository.VillageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final AdminRepository adminRepository;
	private final VillageRepository villageRepository;
	private final UserRepositoryImpl userRepositoryImpl;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${user.password}")
	private String PASSWORD;

	public List<UserResponse> findAll() {
		return userRepository.findAll().stream()
			.filter(user -> user.getRole().equals(Role.ROLE_USER))
			.map(user -> UserResponse.from(user))
			.collect(Collectors.toList());
	}

	@Transactional
	public UserResponse register(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
		}
		User user = User.builder()
			.username("TEMP_NAME")
			.role(Role.ROLE_USER)
			.password(bCryptPasswordEncoder.encode(PASSWORD))
			.email(email)
			.build();
		userRepository.save(user);

		return UserResponse.from(user);
	}

	@Transactional
	public UserResponse registerInformation(Long userId, UserCreateRequest userCreateRequest) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

		user.registerInformation(userCreateRequest);

		return UserResponse.from(user);
	}

	public UserResponse findByEmail(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		return UserResponse.from(user);
	}

	public UserResponse findByPhoneNumber(String phoneNumber) {
		User user = userRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
		return UserResponse.from(user);
	}

	@Transactional
	public void delete(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		if (Optional.ofNullable(user.getDevice()).isPresent()) {
			user.getDevice().removeUser();
		}

		if (Optional.ofNullable(user.getVillage()).isPresent()) {
			user.getVillage().removeUser(user);
		}

		if (Optional.ofNullable(user.getGuardians()).isPresent()) {
			user.removeGaurdian();
		}

		if (Optional.ofNullable(user.getWard()).isPresent()) {
			user.removeWard();
		}

		userRepository.delete(user);
	}

	@Transactional
	public AdminResponse changeToAdmin(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		if (!Objects.isNull(user.getVillage().getAdmin())) {
			throw new IllegalArgumentException("해당 마을에 이미 이장이 존재합니다.");
		}

		Admin admin = user.toAdmin();

		userRepository.delete(user);
		adminRepository.save(admin);

		return AdminResponse.from(admin);
	}

	public DeviceResponse findDeviceById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		if (Objects.isNull(user.getDevice())) {
			return null;
		}
		return DeviceResponse.from(user.getDevice());
	}

	public VillageResponse findVillageById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		if (user.getVillage() == null) {
			return null;
		}

		return VillageResponse.from(user.getVillage());
	}

	@Transactional
	public void registerVillage(Long id, Long villageId) {
		log.info("---[USER] REGISTER VILLAGE---");
		log.info("USER-ID : {}, VILLAGE-ID : {}", id, villageId);
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		Village village = villageRepository.findById(villageId)
			.orElseThrow(() -> new IllegalArgumentException("해당 마을이 존재하지 않습니다."));

		user.registerVillage(village);
	}

	public UserResponse getWard(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		return UserResponse.from(user.getWard());
	}

	public List<UserResponse> getGaurdians(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

		return user.getGuardians().stream()
			.map(u -> UserResponse.from(u))
			.collect(Collectors.toList());
	}

	@Transactional
	public void registerGaurdian(Long id, Long guardianId) {
		User ward = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.."));

		User guardian = userRepository.findById(guardianId)
			.orElseThrow(() -> new IllegalArgumentException("해당 보호자 정보가 존재하지 않습니다."));

		ward.addGaurdian(guardian);
	}

	public List<UserResponse> search(Long villageId, String username) {
		return userRepositoryImpl.searchBy(villageId, username);
	}

	@Transactional
	public UserResponse registerDeviceUser(DeviceUserCreateRequest deviceUserCreateRequest) {
		log.info("username = {}", deviceUserCreateRequest.getUsername());
		User user = deviceUserCreateRequest.toEntity();
		userRepository.save(user);
		return UserResponse.from(user);
	}
}
