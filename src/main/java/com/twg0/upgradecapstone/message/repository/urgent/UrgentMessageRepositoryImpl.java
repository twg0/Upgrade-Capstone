package com.twg0.upgradecapstone.message.repository.urgent;

import static com.twg0.upgradecapstone.device.domain.QDevice.*;
import static com.twg0.upgradecapstone.message.domain.QUrgentMessage.*;
import static com.twg0.upgradecapstone.user.domain.QUser.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twg0.upgradecapstone.device.domain.Device;
import com.twg0.upgradecapstone.message.domain.UrgentMessage;
import com.twg0.upgradecapstone.message.dto.UrgentMessageReponse;
import com.twg0.upgradecapstone.user.domain.User;
import com.twg0.upgradecapstone.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UrgentMessageRepositoryImpl implements CustomUrgentMessageRepository{

	private final JPAQueryFactory jpaQueryFactory;
	private final UserRepository userRepository;
	
	@Override
	public List<UrgentMessageReponse> findAllByUserId(Long userId) {
		
		User findUser = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
		
		Device findDevice = findUser.getDevice();
		if(findDevice == null) {
			throw new IllegalArgumentException("회원의 단말기가 없습니다.");
		}
		
		log.info("device ID = {},",findDevice.getId());
		
		List<UrgentMessage> list = jpaQueryFactory.selectFrom(urgentMessage)
		 			.leftJoin(urgentMessage.device, device).fetchJoin()
		 			.leftJoin(device.user, user).fetchJoin()
					.where(urgentMessage.device.id.eq(findDevice.getId()))
					.fetch();

		return list.stream().map(u -> UrgentMessageReponse.from(u)).collect(Collectors.toList());
	}

}
