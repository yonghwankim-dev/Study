package BOJ.BOJ_2750;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


class Main {
	public static void solution(int[] arr){
		Arrays.sort(arr);
		Arrays.stream(arr).forEach(System.out::println);
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		for(int i = 0; i < n; i++){
			arr[i] = Integer.parseInt(br.readLine());
		}
		Main.solution(arr);
	}
}
