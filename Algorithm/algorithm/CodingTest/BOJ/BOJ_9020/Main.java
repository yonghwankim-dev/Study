package BOJ.BOJ_9020;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static boolean isPrime(int n)
	{
		for(int i=2;i<=(int)Math.sqrt(n);i++)
		{
			if(n%i==0)
			{
				return false;
			}
		}
		return true;
	}
	
	public static String solution(int n)
	{
		int min = Integer.MAX_VALUE;
		int p1=0,p2=0;
		for(int i=2; i<n; i++)
		{
			if(isPrime(i))
			{
				int a = n-i;
				if(isPrime(a))
				{
					if(Math.abs(i-a)<min)
					{
						min = Math.abs(i-a);
						p1 = i;
						p2 = a;
					}
				}
			}
		}
		return p1 + " " + p2;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		
		for(int i=0;i<tc;i++)
		{
			System.out.println(solution(Integer.parseInt(br.readLine())));
		}
	}
}
