package BOJ.BOJ_9095;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(int n)
	{
		if(n<=2)
		{
			return n;
		}else if(n==3)
		{
			return n+1;
		}
		else
		{
			return solution(n-1) + solution(n-2) + solution(n-3);
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		

		int tc = Integer.parseInt(br.readLine());
		
		for(int i=0;i<tc;i++)
		{
			int n = Integer.parseInt(br.readLine());
			System.out.println(solution(n));
		}

		
	}
}
