package BOJ.BOJ_10870;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(int[] arr, int n)
	{
		if(n==0)
		{
			return 0;
		}
		else if(n==1)
		{
			arr[n] = 1; 
			return 1;
		}
		else
		{
			if(arr[n]!=0)
			{
				return arr[n];
			}else 
			{
				arr[n] = solution(arr, n-1) + solution(arr, n-2); 
				return arr[n];
			}
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n+1];
		
		int answer = solution(arr, n);
		System.out.println(answer);
		
		
		
	}
}
