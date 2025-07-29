package BOJ.BOJ_2455;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int people = 0;
		int answer = 0;
		for(int i=0;i<4;i++)
		{
			String str[] = br.readLine().split(" ");
			int out = Integer.parseInt(str[0]);
			int in = Integer.parseInt(str[1]);
			people = people - out + in;
			
			if(people>answer)
			{
				answer = people;
			}
		}
		System.out.println(answer);
		
	}
}
