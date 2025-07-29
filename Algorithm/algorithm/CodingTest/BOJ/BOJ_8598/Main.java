package BOJ.BOJ_8598;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void solution() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int testcase = Integer.parseInt(br.readLine());
		for(int i=0;i<testcase;i++)
		{
			String str = br.readLine();
			int count =0;
			int answer = 0;
			for(int j=0;j<str.length();j++)
			{
				if(str.charAt(j)=='O')
				{
					count++;
					answer += count;
				}else 
				{
					count = 0;
				}
			}
			System.out.println(answer);
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		solution();
	}
}
