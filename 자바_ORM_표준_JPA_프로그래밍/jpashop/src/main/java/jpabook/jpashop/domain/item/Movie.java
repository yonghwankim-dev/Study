package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue(value = "M")
public class Movie extends Item {
	private String director;
	private String actor;

	@Builder
	public Movie(Long id, String name, int price, int stockQuantity, String director, String actor) {
		super(id, name, price, stockQuantity);
		this.director = director;
		this.actor = actor;
	}

	protected Movie() {
		super(null, null, 0, 0);
	}
}
