package BOJ.BOJ_2981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	
	
	public static String solution(int[] arr, int n)
	{
		StringBuilder sb = new StringBuilder();
		for(int m=2;m<arr[n-1];m++)
		{
			
		}
		return sb.toString().trim();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		
		for(int i=0; i<n;i++)
		{
			arr[i] = Integer.parseInt(br.readLine());
		}
		Arrays.sort(arr);
		System.out.println(solution(arr, n));
	}
}
