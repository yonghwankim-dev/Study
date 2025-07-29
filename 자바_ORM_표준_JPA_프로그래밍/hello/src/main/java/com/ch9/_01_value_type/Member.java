package com.ch9._01_value_type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue
	private Long id;

	// 값 타입 name, age
	private String name;
	private int age;

}
