package BOJ.BOJ_10988;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(String str)
	{
		StringBuilder sb = new StringBuilder(str);
		String rever_str = sb.reverse().toString();
		
		return str.equals(rever_str) ? 1 : 0;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str = br.readLine();
		System.out.println(solution(str));
	}
}
