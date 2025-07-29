package com.ch7._03_table_per_class_strategy;

import javax.persistence.Entity;

@Entity
public class Album extends Item {
	private String artist;
}
