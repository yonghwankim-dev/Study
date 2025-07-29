package BOJ.BOJ_11655;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static String solution(String str)
	{
		StringBuilder sb = new StringBuilder();
		
		byte[] bytes = str.getBytes();
		for(byte b : bytes)
		{
			int a = b+13;
			if(b>=65 && b<=90)
			{	
				if(b+13>90)
				{
					a = (b+13)-91+65;
				}
				sb.append((char)a);
			}
			else if(b>=97 && b<=122)
			{
				if(b+13>122)
				{
					a = (b+13)-123+97;
				}
				sb.append((char)a);
			}
			else
			{
				sb.append((char)b);
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(solution(str));
	}
}
