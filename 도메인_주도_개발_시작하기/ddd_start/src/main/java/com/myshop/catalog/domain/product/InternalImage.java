package com.myshop.catalog.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("II") // Internal Image
public class InternalImage extends Image {
	protected InternalImage() {
	}

	public InternalImage(String path) {
		super(path);
	}

	@Override
	public String getURL() {
		return "/images/original/" + getPath();
	}

	@Override
	public boolean hasThumbnail() {
		return true;
	}

	@Override
	public String getThumbnailURL() {
		return "/images/thumbnail/" + getPath();
	}
}
