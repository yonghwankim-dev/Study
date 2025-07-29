package BOJ.BOJ_1259;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static String solution(String str)
	{
		StringBuilder sb = new StringBuilder(str);
		String rever_str = sb.reverse().toString();
		
		return str.equals(rever_str) ? "yes" : "no";
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String in = "";
		while(!(in=br.readLine()).equals("0"))
		{
			System.out.println(solution(in));
		}
		
	}
}
