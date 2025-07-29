package com.ch7._08_one_to_one_identity_relation_mapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class BoardDetail {
	@Id
	private Long boardId;

	@MapsId // BoardDetail.boardId 매핑
	@OneToOne
	@JoinColumn(name = "BOARD_ID")
	private Board board;

	private String content;
}
