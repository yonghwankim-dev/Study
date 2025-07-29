package BOJ.BOJ_10809;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] answer = new int[26];
		Arrays.fill(answer, -1);
	
		String str = br.readLine();
		
		for(int i=0;i<str.length();i++)
		{
			int p = str.charAt(i)-97;
			if(answer[p] == -1)
			{
				answer[p] = i;
			}	
		}
		
		for(int i=0;i<answer.length;i++)
		{
			System.out.print(answer[i] + " ");
		}
	}
}
