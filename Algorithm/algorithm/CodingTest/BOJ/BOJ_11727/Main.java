package BOJ.BOJ_11727;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static long[] arr = new long[1001];
	public static long solution(int n)
	{
		if(n==1)
		{
			arr[n] = 1;
			return 1;
		}
		if(n==2)
		{
			arr[n] = 3;
			return 3;
		}
		// f(n-1) + 2*f(n-2)
		long first = 0;
		long second = 0;
		
		if(arr[n-1]!=0)
		{
			first = arr[n-1];
		}
		else
		{
			first = arr[n-1] = solution(n-1) % 10007;
		}
		
		
		if(arr[n-2]!=0)
		{
			second = 2*arr[n-2]; 
		}
		else
		{
			second = arr[n-2] = 2*solution(n-2) % 10007;
		}
		
		return first + second;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n)%10007);
		
	}
}
