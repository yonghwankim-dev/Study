package BOJ.BOJ_9663;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	static boolean[] col;
	static boolean[] diag1;
	static boolean[] diag2;
	static long answer;
	public static void findNQueen(int n, int y)
	{
		if(y==n)
		{
			answer++;
			return;
		}
		for(int x=0;x<n;x++)
		{
			if(col[x] || diag1[x+y] || diag2[x-y+n-1])
			{
				continue;
			}
			col[x] = diag1[x+y] = diag2[x-y+n-1] = true;
			findNQueen(n, y+1);
			col[x] = diag1[x+y] = diag2[x-y+n-1] = false;
		}
	}
	
	public static long solution(int n)
	{
		col = new boolean[n*2+1];
		diag1 = new boolean[n*2+1];
		diag2 = new boolean[n*2+1];
		answer=0;
		findNQueen(n,0);
	
		return answer;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
		
	}
}
