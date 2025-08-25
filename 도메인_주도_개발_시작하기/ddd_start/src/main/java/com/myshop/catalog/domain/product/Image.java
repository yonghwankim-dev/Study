package com.myshop.catalog.domain.product;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "image_type")
@Table(name = "image")
public abstract class Image {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long id;
	@Column(name = "image_path")
	private String path;
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	protected Image() {
	}

	public Image(String path) {
		this.path = path;
		this.updateTime = LocalDateTime.now();
	}

	protected String getPath() {
		return path;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public abstract String getURL();

	public abstract boolean hasThumbnail();

	public abstract String getThumbnailURL();
}
