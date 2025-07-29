package BOJ.BOJ_11721;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str = br.readLine();
		
		int i=0;
		while(i<str.length())
		{
			String answer;
			if(i+10>=str.length())
			{
				answer = str.substring(i);
			}
			else
			{
				answer = str.substring(i, i+10);
			}
			
			i+=10;
			System.out.println(answer);
		}

		
	}
}
