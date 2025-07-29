package com.jpabook.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.TableGenerator;

import lombok.Getter;

@Getter
@Entity
@TableGenerator( // 3. 방버3 : TABLE 방식
	name = "BOARD_SEQ_GENERATOR",
	table = "MY_SEQUENCES",
	pkColumnValue = "BOARD_SEQ", allocationSize = 50
)
@SequenceGenerator( // 2. 방법2 : SEQUENCES 방식
	name = "BOARD_SEQUENCES_SEQ_GENERATOR",
	sequenceName = "BOARD_SEQ", // 매핑할 데이터베이스 시퀀스 이름
	initialValue = 1, allocationSize = 50
)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 1. 방법1 : IDENTITY 방식
	//    @GeneratedValue(strategy = GenerationType.AUTO) // 4. 방법4: AUTO 방식
	//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
	private Long id;
}
