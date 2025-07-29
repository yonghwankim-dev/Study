package org.example.ch3_gridy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Solution1Test {

	@DisplayName("배열이 주어질때 배열의 수를 m번 더하여 가장 큰수를 만들어낸다.")
	@Test
	void solution1(){
	    // given
	    int[] arr = new int[]{2, 4, 5, 4, 6};
		int m = 8;
		int k = 3;
	    // when
		int actual = new Solution1().solution(arr, m, k);
		// then
		Assertions.assertEquals(46, actual);
	}

	@DisplayName("배열이 주어질때 배열의 수를 공식을 이용해서 가장 큰수를 만들어낸다.")
	@Test
	void solution2(){
		// given
		int[] arr = new int[]{2, 4, 5, 4, 6};
		int m = 8;
		int k = 3;
		// when
		int actual = new Solution1().solution2(arr, m, k);
		// then
		Assertions.assertEquals(46, actual);
	}

	@DisplayName("배열이 주어질때 배열의 수를 공식을 이용해서 가장 큰수를 만들어낸다.")
	@Test
	void solution2_1(){
		// given
		int[] arr = new int[]{2, 4, 5, 4, 6};
		int m = 9;
		int k = 3;
		// when
		int actual = new Solution1().solution2(arr, m, k);
		// then
		Assertions.assertEquals(52, actual);
	}

}
