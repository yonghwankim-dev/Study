package org.example.ch5_dfs_bfs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IceTest {

	@Test
	void test(){
	    // given
		Ice ice = new Ice();
		int[][] map = {{0, 0, 1, 1, 0}, {0, 0, 0, 1, 1}, {1, 1, 1, 1, 1}, {0, 0, 0, 0, 0}};
		// when
		int answer = ice.solution(map);
		// then
		Assertions.assertEquals(3, answer);
	}

}
