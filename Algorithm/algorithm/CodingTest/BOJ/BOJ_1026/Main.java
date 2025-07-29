package BOJ.BOJ_1026;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

public class Main {
	
	
	public static int solution(Integer[] A, Integer[] B, int n)
	{
		Arrays.sort(A);	// 오름차순 정렬
		
		Arrays.sort(B,Collections.reverseOrder());	// 내림차순 정렬
		
		// A*B 합수행
		int answer = 0;
		for(int i=0;i<n;i++)
		{
			answer += (A[i]*B[i]);
		}
	
		return answer;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		Integer[] A = new Integer[n];
		Integer[] B = new Integer[n];
		
		A = Arrays.stream(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray()).boxed()
				.toArray(Integer[]::new);	
		B = Arrays.stream(Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray()).boxed()
				.toArray(Integer[]::new);
		
		System.out.println(solution(A, B, n));
	}
}
