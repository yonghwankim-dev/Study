package BOJ.BOJ_1874;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Integer> stack = new Stack<Integer>();
		
		int n = Integer.parseInt(br.readLine());
		List<String> answer = new ArrayList<String>();
		int start = 0;
		
		while(n-->0)
		{
			int value = Integer.parseInt(br.readLine());
			
			if(value>start)
			{
				for(int i=start+1;i<=value;i++)
				{
					stack.push(i);
					answer.add("+");
				}
				start = value;
			}
			else if(stack.peek()!=value)
			{
				System.out.println("NO");
				return;
			}
			stack.pop();
			answer.add("-");
		}
		
		for(String s : answer)
		{
			System.out.print(s + " ");
		}
		
		
		
	}
}
