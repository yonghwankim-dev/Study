package org.example.ch7_binary_search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecursiveBinarySearchTest {

	@Test
	void test(){
	    // given
		RecursiveBinarySearch recursiveBinarySearch = new RecursiveBinarySearch();
		int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
		// when
		int search1 = recursiveBinarySearch.search(arr, 1, 0, arr.length - 1);
		int search2 = recursiveBinarySearch.search(arr, 4, 0, arr.length - 1);
		int search3 = recursiveBinarySearch.search(arr, 13, 0, arr.length - 1);
		int search4 = recursiveBinarySearch.search(arr, 19, 0, arr.length - 1);
		// then
		Assertions.assertEquals(0, search1);
		Assertions.assertEquals(-1, search2);
		Assertions.assertEquals(6, search3);
		Assertions.assertEquals(9, search4);
	}

}
