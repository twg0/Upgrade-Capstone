package com.twg0.upgradecapstone.file.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.twg0.upgradecapstone.admin.domain.Admin;
import com.twg0.upgradecapstone.village.domain.Village;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class File {
	@Id
	@GeneratedValue
	@Column(name = "FILE_ID")
	@NotNull
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@NotNull
	private Admin admin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VILLAGE_ID")
	@NotNull
	private Village village;

	@NotNull
	@Size(max = 30)
	@Column(length = 30)
	private String title;

	@NotNull
	@Size(max = 100)
	@Column(length = 100)
	private String contents;

	@CreatedDate
	private LocalDateTime createdTime;

	@Builder
	public File(Long id, Admin admin, Village village, String title,
		String contents, LocalDateTime createdTime) {
		this.admin = admin;
		this.village = village;
		this.title = title;
		this.contents = contents;
		this.createdTime = createdTime;
	}

}
