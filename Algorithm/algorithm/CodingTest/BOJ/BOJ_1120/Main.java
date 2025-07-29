package BOJ.BOJ_1120;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

	
	
	public static int solution(String A, String B)
	{
		StringBuilder a_sb = new StringBuilder(A);
		StringBuilder b_sb = new StringBuilder(B);
		
		while(a_sb.length()!=b_sb.length())
		{
			if(a_sb.charAt(0)!=b_sb.charAt(0))
			{
				a_sb.insert(0, b_sb.charAt(0));
			}
			else if(a_sb.charAt(a_sb.length()-1)!=b_sb.charAt(b_sb.length()-1))
			{
				a_sb.append(b_sb.charAt(b_sb.length()-1));
			}
			else
			{
				a_sb.insert(0, b_sb.charAt(0));
			}
		}
		
		int answer = 0;
		for(int i=0;i<b_sb.length();i++)
		{
			if(a_sb.charAt(i)!=b_sb.charAt(i))
			{
				answer++;
			}
		}
		
		return answer;
	}

	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	}
}
