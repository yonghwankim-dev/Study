package BOJ.BOJ_7567;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static int solution(String str[])
	{
		int answer = 0;
		int height = 0;
		
		for(int i=0;i<str.length;i++)
		{
			if(i==0)
			{
				height = 10;
			}
			else
			{
				if(str[i].equals(str[i-1]))
				{
					height +=5;
				}
				else
				{
					height +=10;
				}
			}
		}
		
		answer = height;
		
		return answer;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split("");
		System.out.println(solution(str));
		
	}
}
