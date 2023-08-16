package com.twg0.upgradecapstone.village.dto;

import com.twg0.upgradecapstone.village.domain.Address;
import com.twg0.upgradecapstone.village.domain.Location;
import com.twg0.upgradecapstone.village.domain.Village;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VillageCreateRequest {
	
	@NotBlank
	@Size(max = 20)
	private String nickname;
	
	@NotBlank
	@Size(max=30)
	private String state; 
	
	@NotBlank
	@Size(max = 30)
	private String city; 
 	
	@NotBlank
	@Size(max = 30)
	private String town; 
	
	@NotNull
	private double longitude;
	
	@NotNull
	private double latitude;
	
	public Village toEntity() {
		Address address = Address.builder().city(city).state(state).town(town).build();
		Location location = Location.builder().longitude(longitude).latitude(latitude).build();
		
		return Village.builder()
					.nickname(nickname)
					.address(address)
					.location(location)
					.build();
	}
}
