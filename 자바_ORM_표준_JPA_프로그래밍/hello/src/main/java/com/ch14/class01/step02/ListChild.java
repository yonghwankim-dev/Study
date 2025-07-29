package com.ch14.class01.step02;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ListChild {
	@Id
	@GeneratedValue
	private Long id;
}
