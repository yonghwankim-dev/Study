package org.example.ch5_dfs_bfs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MonsterTest {

	@Test
	void test(){
	    // given
		Monster monster = new Monster();
		int[][] map = {{1, 1, 0}, {0, 1, 0}, {0, 1, 1}};
		// when
		int answer = monster.solution(3, 3, map);
		// then
		Assertions.assertEquals(5, answer);
	}
}
