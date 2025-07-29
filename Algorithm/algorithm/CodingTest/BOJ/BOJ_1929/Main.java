package BOJ.BOJ_1929;

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

	public static void solution(int start, int end)
	{
		for(int i=start; i<=end; i++)
		{
			if(isPrime(i) && i>1)
			{
				System.out.println(i);
			}
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		int start = Integer.parseInt(str[0]);
		int end = Integer.parseInt(str[1]);
		
		solution(start, end);
		
	}
}
