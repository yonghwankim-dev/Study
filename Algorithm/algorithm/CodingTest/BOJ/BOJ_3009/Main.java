package BOJ.BOJ_3009;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static String solution(int[][] arr)
	{
		String answer = "";
		
		if(arr[0][0]==arr[1][0])
		{
			answer += (arr[2][0] + " ");
		}
		else if(arr[0][0]==arr[2][0])
		{
			answer += (arr[1][0] + " ");
		}
		else
		{
			answer += (arr[0][0] + " ");
		}
		
		if(arr[0][1]==arr[1][1])
		{
			answer += (arr[2][1]);
		}
		else if(arr[0][1]==arr[2][1])
		{
			answer += (arr[1][1]);
		}
		else
		{
			answer += (arr[0][1]);
		}
		
		return answer;
	}
	
	public static String solution2(int[][] arr)
	{
		int x = arr[0][0]^arr[1][0]^arr[2][0];
		int y = arr[0][1]^arr[1][1]^arr[2][1];
		return x + " " + y;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		int[][] arr = new int[3][2]; 
		
		
		for(int i=0;i<3;i++)
		{
			String[] str = br.readLine().split(" ");
			arr[i][0] = Integer.parseInt(str[0]);
			arr[i][1] = Integer.parseInt(str[1]);
		}

		System.out.println(solution2(arr));
	}
}
