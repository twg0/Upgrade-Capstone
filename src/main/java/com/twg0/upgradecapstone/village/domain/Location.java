package com.twg0.upgradecapstone.village.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {

	@Column(nullable = false)
	private double longitude;
	@Column(nullable = false)
	private double latitude;
	
}
