package com.twg0.upgradecapstone.device.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.timcook.capstone.file.domain.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Confirm {

	@Id @GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DEVICE_ID")
	private Device device;
	
	@OneToOne
	@JoinColumn(name = "FILE_ID")
	private File file;
	
	@CreatedDate
	private LocalDateTime createdTime;
}
