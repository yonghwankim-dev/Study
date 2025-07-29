package com.ch15.class04.step06;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
