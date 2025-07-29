package BOJ.BOJ_10799;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


public class Main {
	public static int solution(String[] input)
	{
		Stack<String> stack = new Stack<String>();
		int answer = 0;
		for(int i=0;i<input.length;i++)
		{
			if(input[i].equals("("))
			{
				stack.add(input[i]);
			}
			else
			{
				stack.pop();
				if(input[i-1].equals("("))	// 레이저
				{
					answer += stack.size();
				}
				else						// 파이프
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
		String[] input = br.readLine().split("");
		
		System.out.println(solution(input));
		
		
	}
}
