package BOJ.BOJ_10798;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static String solution(String[][] board)
	{
		StringBuilder sb = new StringBuilder();
		
		for(int col=0;col<15;col++)
		{
			for(int row=0;row<5;row++)
			{
				if(!board[row][col].equals(""))
				{
					sb.append(board[row][col]);
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[][] board = new String[15][15];
		
		for(int i=0;i<5;i++)
		{
			String[] str = br.readLine().split("");
			for(int j=0;j<15;j++)
			{
				if(j<str.length)
				{
					board[i][j] = str[j];
				}
				else
				{
					board[i][j] = "";
				}
				
			}
		}
		System.out.println(solution(board));
	}
}
