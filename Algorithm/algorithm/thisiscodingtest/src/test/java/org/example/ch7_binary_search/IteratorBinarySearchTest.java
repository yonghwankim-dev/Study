package org.example.ch7_binary_search;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IteratorBinarySearchTest {
	@Test
	void test(){
		// given
		IteratorBinarySearch binarySearch = new IteratorBinarySearch();
		int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
		// when
		int search1 = binarySearch.search(arr, 1, 0, arr.length - 1);
		int search2 = binarySearch.search(arr, 4, 0, arr.length - 1);
		int search3 = binarySearch.search(arr, 13, 0, arr.length - 1);
		int search4 = binarySearch.search(arr, 19, 0, arr.length - 1);
		// then
		Assertions.assertEquals(0, search1);
		Assertions.assertEquals(-1, search2);
		Assertions.assertEquals(6, search3);
		Assertions.assertEquals(9, search4);
	}
}
