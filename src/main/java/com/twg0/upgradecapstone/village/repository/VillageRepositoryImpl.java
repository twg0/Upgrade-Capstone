package com.twg0.upgradecapstone.village.repository;

import static com.twg0.upgradecapstone.device.domain.QDevice.*;
import static com.twg0.upgradecapstone.file.domain.QFile.*;
import static com.twg0.upgradecapstone.message.domain.QDetectMessage.*;
import static com.twg0.upgradecapstone.message.domain.QUrgentMessage.*;
import static com.twg0.upgradecapstone.user.domain.QUser.*;
import static com.twg0.upgradecapstone.village.domain.QVillage.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twg0.upgradecapstone.device.dto.DeviceResponse;
import com.twg0.upgradecapstone.file.dto.FileResponse;
import com.twg0.upgradecapstone.file.repository.FileRepository;
import com.twg0.upgradecapstone.message.domain.DetectMessage;
import com.twg0.upgradecapstone.message.domain.UrgentMessage;
import com.twg0.upgradecapstone.message.dto.DetectMessageResponse;
import com.twg0.upgradecapstone.message.dto.UrgentMessageReponse;
import com.twg0.upgradecapstone.user.dto.UserResponse;
import com.twg0.upgradecapstone.village.domain.Village;
import com.twg0.upgradecapstone.village.dto.VillageResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VillageRepositoryImpl implements CustomVillageRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private final FileRepository fileRepository;

	@Transactional
	@Override
	public List<DeviceResponse> findAllDevices(Long id) {

		Village findVillage = jpaQueryFactory
			.select(village)
			.from(village)
			.leftJoin(village.devices, device).fetchJoin()
			.leftJoin(device.user, user).fetchJoin()
			.where(village.id.eq(id))
			.fetchOne();
		log.info("=Village -> DeviceResponse=");
		return findVillage.getDevices().stream()
			.map(d -> DeviceResponse.from(d))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<FileResponse> findAllFiles(Long id) {
		Village findVillage = jpaQueryFactory
			.select(village)
			.from(village)
			.leftJoin(village.files, file).fetchJoin()
			.where(village.id.eq(id))
			.fetchOne();

		return findVillage.getFiles().stream()
			.map(f -> FileResponse.from(f))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<UserResponse> findAllUsers(Long id) {
		Village findVillage = jpaQueryFactory
			.select(village)
			.from(village)
			.leftJoin(village.users, user).fetchJoin()
			.where(village.id.eq(id))
			.fetchOne();
		return findVillage.getUsers().stream()
			.map(u -> UserResponse.from(u))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<UrgentMessageReponse> findAllUrgentMessages(Long id) {

		List<UrgentMessage> list = jpaQueryFactory.selectFrom(urgentMessage)
			.leftJoin(urgentMessage.device, device).fetchJoin()
			.leftJoin(device.village, village).fetchJoin()
			.where(village.id.eq(id))
			.fetch();

		return list.stream()
			.map(m -> UrgentMessageReponse.from(m))
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public List<DetectMessageResponse> findAllDetectMessages(Long id) {

		List<DetectMessage> list = jpaQueryFactory.selectFrom(detectMessage)
			.leftJoin(detectMessage.device, device).fetchJoin()
			.leftJoin(device.village, village).fetchJoin()
			.where(village.id.eq(id))
			.fetch();

		return list.stream()
			.map(m -> DetectMessageResponse.from(m))
			.collect(Collectors.toList());

	}

	@Override
	public List<VillageResponse> searchBy(String words) {
		List<Village> villages = jpaQueryFactory.select(village)
			.from(village)
			.where(containsAddress(words).or(containsNickname(words)))
			.fetch();

		return villages.stream()
			.map(v -> VillageResponse.from(v))
			.collect(Collectors.toList());
	}

	private BooleanExpression containsAddress(String address) {
		if (StringUtils.isBlank(address)) {
			return null;
		}

		return village.address.state.contains(address)
			.or(village.address.city.contains(address))
			.or(village.address.town.contains(address));
	}

	private BooleanExpression containsNickname(String nickname) {
		if (StringUtils.isBlank(nickname)) {
			return null;
		}

		return village.nickname.contains(nickname);
	}

}
