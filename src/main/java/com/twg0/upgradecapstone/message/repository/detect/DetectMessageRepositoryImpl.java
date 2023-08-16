package com.twg0.upgradecapstone.message.repository.detect;

import static com.timcook.capstone.device.domain.QDevice.*;
import static com.timcook.capstone.message.domain.QDetectMessage.*;
import static com.timcook.capstone.user.domain.QUser.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timcook.capstone.message.domain.DetectMessage;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DetectMessageRepositoryImpl implements CustomDetectMessageRepository{

	private final JPAQueryFactory jpaQueryFactory;
	
	@Override
	public List<DetectMessage> findAllMessagesByUserId(Long userId) {
		
		LocalDateTime to = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		LocalDateTime from = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusMinutes(50);
		
		return  jpaQueryFactory
					.selectFrom(detectMessage)
					.join(detectMessage.device, device).fetchJoin()
					.join(device.user, user).fetchJoin()
					.where(detectMessage.device.user.id.eq(userId)
							.and(
								detectMessage.createdTime.between(from, to)))
					.fetch();
	}

	
	
}
