package org.example.practice.ch8.step01;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;

public class Fibo {
	public static void main(String[] args) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
			int n  = Integer.parseInt(br.readLine());
			int[] answer = new int[n+1];
			for (int i = 0; i <= n; i++){
				answer[i] = solution(i);
			}
			int[] expected = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			System.out.println(Arrays.toString(answer));
			assert Arrays.equals(answer, expected);
		}catch (IOException e){
			throw new IOException(e);
		}
	}

	private static int solution(int x){
		if(x <= 1){
			return x;
		}
		return solution(x - 1) + solution(x - 2);
	}
}
