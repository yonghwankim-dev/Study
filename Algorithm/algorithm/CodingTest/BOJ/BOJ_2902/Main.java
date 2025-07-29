package BOJ.BOJ_2902;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		String str[] = br.readLine().split("-");
		for(String s : str)
		{
			answer.append(s.charAt(0));
		}
		System.out.println(answer);
		
	}
}
