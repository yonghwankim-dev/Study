package org.example.ch11_gridy.step02;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Solution {
	public static void main(String[] args) {
		String s = "02984";
		int answer = solution(s);
		System.out.println(answer);
	}
	
	public static int solution(String s){
		int[] numbers = Arrays.stream(s.split(""))
			.mapToInt(Integer::parseInt)
			.toArray();
		int result = numbers[0];
		for (int i = 1; i < numbers.length; i++){
			int number = numbers[i];
			if (number <= 1 || result <= 1){
				result += number;
			}else{
				result *= number;
			}
		}
		return result;
	}
}
