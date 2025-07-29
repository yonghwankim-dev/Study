package com.ch7._08_one_to_one_identity_relation_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "BOARD_ID")
	private Long id;
	private String title;

	@OneToOne(mappedBy = "board")
	private BoardDetail boardDetail;
}
