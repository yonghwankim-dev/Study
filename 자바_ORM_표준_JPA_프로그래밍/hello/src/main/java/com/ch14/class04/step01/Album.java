package com.ch14.class04.step01;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
public class Album extends Item{
	private String artist;
	private String etc;
}
