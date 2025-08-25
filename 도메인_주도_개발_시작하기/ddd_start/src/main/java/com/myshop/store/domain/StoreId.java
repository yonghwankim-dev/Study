package com.myshop.store.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class StoreId implements Serializable {
	@Column(name = "store_id")
	private String id;

	protected StoreId() {
	}

	public StoreId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		StoreId storeId = (StoreId)object;
		return Objects.equals(id, storeId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
