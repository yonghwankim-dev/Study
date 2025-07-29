package CODEJAM.jam2021.qualification_round.p03_reversort_enginering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution2 {
	static void reverse(int[] arr, int i, int j)
	{
		while(i < j)
		{
			int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
			
			i++;
			j--;
		}
	}
	static String reversortEngineering(int N, int C) {
		if(C < N-1 || C > ((N * (N+1)) / 2) -1) 
		{
			return "IMPOSSIBLE";
		}
		
		// 배열에 1..N을 저장
		int[] arrOfRange = new int[N];
		for(int i = 0; i < N; i++)
		{
			arrOfRange[i] = i + 1;
		}
		
		for(int i = N - 2; i >= 0; i--)
		{
			int j = Math.min(C - i, N - i);
			System.out.printf("C : %d, i : %d, C - i : %d, N - i : %d, j : %d\n",C, i, C - i, N - i, j);
			C -= j;
			
			reverse(arrOfRange, i, i + j - 1);
		}
		
		// 배열에 있는 값들을 결과 변수에 저장
		String response = "";
		for(int i = 0; i < N; i++)
		{
			String temp = Integer.toString(arrOfRange[i]);
			response += (temp + " ");
		}
		
		
		return response;
	}	 
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		int N = 0;
		int C = 0;
		String[] line = null;
		
		for(int i=0; i<tc; i++)
		{
			line = br.readLine().split(" ");
			
			N = Integer.parseInt(line[0]);
			C = Integer.parseInt(line[1]);
			System.out.printf("case #%d: %s\n",i+1, reversortEngineering(N, C));
		}
	}


}
