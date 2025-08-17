package com.myshop.catalog.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EI") // External Image
public class ExternalImage extends Image {
	protected ExternalImage() {
	}

	public ExternalImage(String path) {
		super(path);
	}

	@Override
	public String getURL() {
		return getPath();
	}

	@Override
	public boolean hasThumbnail() {
		return false;
	}

	@Override
	public String getThumbnailURL() {
		return null;
	}
}
