package BOJ.BOJ_2798;


import org.junit.Test;

import java.util.Arrays;


class MainTest {

	@Test
	void test1() {
		int n = 5;
		int m = 21;
		int[] cards = {5,6,7,8,9};
		
		System.out.println(Main.solution(n, m, cards));
	}
	
	@Test
	void test2() {
		int n = 10;
		int m = 500;
		int[] cards = {93,181,245,214,315,36,185,138,216,295};
		
		Arrays.sort(cards);
		System.out.println(Main.solution(n, m, cards));
	}
	
	@Test
	void test_solution1() {
		int n =100;
		int m = 300000;
		int[] cards = new int[100];
		
		for(int i=0;i<cards.length;i++)
		{
			cards[i] = i+1;
		}
		
		System.out.println(Main.solution(n, m, cards));
	}
	
	@Test
	void test_solution2() {
		int n =100;
		int m = 300000;
		int[] cards = new int[100];
		
		for(int i=0;i<cards.length;i++)
		{
			cards[i] = i+1;
		}
		
		System.out.println(Main.solution(n, m, cards));
	}

}
