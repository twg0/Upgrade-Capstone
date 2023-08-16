package com.twg0.upgradecapstone.village.dto;

import java.util.Optional;

import com.querydsl.core.annotations.QueryProjection;
import com.timcook.capstone.admin.dto.AdminResponse;
import com.timcook.capstone.village.domain.Location;
import com.timcook.capstone.village.domain.Village;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VillageResponse {
	
	private Long id;
	private String nickname;
	private AdminResponse admin;
	private String city;
	private String state;
	private String town;
	private Location location;
	
	@QueryProjection
	public VillageResponse(Long id, String nickname, AdminResponse admin, String city, String state, String town,
			Location location) {
		this.id = id;
		this.nickname = nickname;
		this.admin = admin;
		this.city = city;
		this.state = state;
		this.town = town;
		this.location = location;
	}
	
	public static VillageResponse from(Village village) {
		if(Optional.ofNullable(village.getAdmin()).isEmpty()) {
			return new VillageResponse(village.getId(),village.getNickname() , null, 
					village.getAddress().getCity(), village.getAddress().getState(), village.getAddress().getTown(), village.getLocation());
		}
		else {
			return new VillageResponse(village.getId(),village.getNickname() , AdminResponse.from(village.getAdmin()), 
					village.getAddress().getCity(), village.getAddress().getState(), village.getAddress().getTown(), village.getLocation());
		}
	}
	
}
