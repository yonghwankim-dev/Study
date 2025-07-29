package BOJ.BOJ_9498;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int score;
		String answer = "";

		score = Integer.parseInt(br.readLine());
		
		if(score>=90 && score<=100)
		{
			answer = "A";
		}else if(score>=80 && score<=89)
		{
			answer = "B";
		}else if(score>=70 && score<=79)
		{
			answer = "C";
		}else if(score>=60 && score<=69)
		{
			answer = "D";
		}else {
			answer = "F";
		}
		System.out.print(answer);
	}
}
