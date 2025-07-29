package BOJ.BOJ_1065;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(int n)
	{
		int hund, ten, one;
		int cnt=0;
		
		if(n<100)
		{
			return n;
		}
		
		for(int i=100;i<=n;i++)
		{
			hund = i/100;
			ten = (i%100)/10;
			one = (i%100)%10;
			
			if((hund-ten)==(ten-one))
			{
				cnt++;
			}
		}
		return 99+cnt;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
		
	}
}
