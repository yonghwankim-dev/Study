package com.ch7._10_secondary_table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "BOARD")
@SecondaryTable(name = "BOARD_DETAIL",
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD_DETAIL_ID"))
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "BOARD_ID")
	private Long id;

	private String title;

	@Column(table = "BOARD_DETAIL")
	private String content;
}
