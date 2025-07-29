package BOJ.BOJ_10808;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] answer = new int[26];
		
		String s = br.readLine();
		
		for(int i=0;i<s.length();i++)
		{
			int idx = s.charAt(i)-'a';
			answer[idx]++;
		}
		
		for(int val : answer)
		{
			System.out.print(val + " ");
		}
	}
}
