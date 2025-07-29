package BOJ.BOJ_1037;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	
	public static int solution(int[] arr, int n)
	{
		return arr[0] * arr[n-1];
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		String[] str = br.readLine().split(" ");
		int[] arr = Arrays.stream(str).mapToInt(Integer::parseInt).toArray();	// 진짜 약수들
		Arrays.sort(arr);
		System.out.println(solution(arr, n));	
	}
}
