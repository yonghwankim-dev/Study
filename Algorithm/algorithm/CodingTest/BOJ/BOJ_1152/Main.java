package BOJ.BOJ_1152;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void solution() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String sentence = br.readLine().trim();
		int answer = 0;
		if(sentence.equals(""))
		{
			answer = 0;
		}else {
			String[] words = sentence.split(" ");
			answer = words.length;
		}
		
		System.out.println(answer);
	}
	
	
	public static void main(String args[]) throws IOException
	{
		solution();
	}
}
