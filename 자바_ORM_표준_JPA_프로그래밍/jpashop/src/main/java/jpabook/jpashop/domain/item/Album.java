package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue(value = "A")
public class Album extends Item {
	private String artist;
	private String etc;

	@Builder
	public Album(Long id, String name, int price, int stockQuantity, String artist, String etc) {
		super(id, name, price, stockQuantity);
		this.artist = artist;
		this.etc = etc;
	}

	protected Album() {
		super(null, null, 0, 0);
	}
}
