package org.example.ch12_imple.step01;

import java.io.*;
import java.util.*;

public class Main{
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String score = br.readLine();
		// 중간 위치 찾기
		int mid = score.length() / 2;

		// 0 ~ n까지 분리
		String left = score.substring(0, mid);
		String right = score.substring(mid);

		// 각 자릿수의 합 계산하기
		int leftValue = calculate(left);
		int rightValue = calculate(right);

		if(leftValue == rightValue){
			System.out.println("LUCKY");
		}else{
			System.out.println("READY");
		}
	}

	private static int calculate(String value){
		return Arrays.stream(value.split(""))
			.mapToInt(Integer::valueOf)
			.sum();
	}
}
