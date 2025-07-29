package BOJ.BOJ_4948;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static boolean isPrime(int n)
	{
		for(int i=2; i<=(int)Math.sqrt(n); i++)
		{
			if(n%i==0)
			{
				return false;
			}
		}
		return true;
	}
	public static int solution(int n)
	{
		int start = n+1;
		int end = n*2;
		int cnt=0;
		for(int i=start; i<=end; i++)
		{
			if(isPrime(i) && i>1)
			{
				cnt++;
			}
		}
		return cnt;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(true)
		{
			int n = Integer.parseInt(br.readLine());
			if(n==0)
			{
				break;
			}
			System.out.println(solution(n));
		}
		
	}
}
