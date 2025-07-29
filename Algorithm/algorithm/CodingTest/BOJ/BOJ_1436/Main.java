package BOJ.BOJ_1436;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(int n)
	{
		int six3 = 666;
		int cnt = 0;
		
		while(true)
		{
			if(String.valueOf(six3).contains("666"))
			{
				cnt++;
			}
			if(cnt==n)
			{
				return six3;
			}
			six3++;
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
	}
}
