package BOJ.BOJ_1100;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(String[][] map)
	{
		int answer = 0;
		for(int row=0, col=0; row<8;row++)
		{
			
			if(row%2==0)
			{
				col=0;
			}
			else
			{
				col=1;
			}
			
			for(;col<8;col+=2)
			{
				if(map[row][col].equals("F"))
				{
					answer++;
				}
			}
		}
		return answer;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[][] map = new String[8][8];
		int answer = 0;
		
		for(int i=0;i<8;i++)
		{
			map[i] = br.readLine().split("");
		}
		answer = solution(map);
		System.out.println(answer);
	}
}
