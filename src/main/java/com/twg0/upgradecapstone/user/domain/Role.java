package com.twg0.upgradecapstone.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Role {
	ROLE_USER, ROLE_ADMIN;
}
