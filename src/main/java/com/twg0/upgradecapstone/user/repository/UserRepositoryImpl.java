package com.twg0.upgradecapstone.user.repository;

import static com.twg0.upgradecapstone.user.domain.QUser.*;
import static com.twg0.upgradecapstone.village.domain.QVillage.*;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twg0.upgradecapstone.user.dto.QUserResponse;
import com.twg0.upgradecapstone.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements CustomUserRepository{

	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<UserResponse> searchBy(Long villageId, String username) {
		return queryFactory.select(new QUserResponse(
								user.id, user.username, user.email, user.role, user.phoneNumber, user.address))
							.from(user)
							.where(containsUsername(username).and(villageIdEq(villageId)))
							.fetch();
	}

	
	private BooleanExpression containsUsername(String username) {
		if(StringUtils.isBlank(username)) {
			return null;
		}
		
		return user.username.contains(username);
	}
	
	private BooleanExpression villageIdEq(Long villageId) {
		if(Objects.isNull(villageId)) {
			return null;
		}
		
		return village.id.eq(villageId);
	}
}
