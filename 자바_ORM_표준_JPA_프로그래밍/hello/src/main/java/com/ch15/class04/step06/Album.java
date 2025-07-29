package com.ch15.class04.step06;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends Item {

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
