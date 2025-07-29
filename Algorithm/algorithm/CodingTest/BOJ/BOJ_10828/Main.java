package BOJ.BOJ_10828;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Stack{
	List<Integer> stack;
	
	public Stack()
	{
		stack = new ArrayList<Integer>();
	}
	
	public void push(int x)
	{
		stack.add(x);
	}
	public int pop()
	{
		if(stack.size()==0)
		{
			return -1;
		}else 
		{
			return stack.remove(stack.size()-1);
		}
		
	}
	public int size()
	{
		return stack.size();
	}
	public int empty()
	{
		return (stack.size()==0 ? 1 : 0);
	}
	public int top()
	{
		if(stack.size()==0)
		{
			return -1;
		}else
		{
			return stack.get(stack.size()-1);
		}
		
	}
}

public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int testcase = Integer.parseInt(br.readLine());
		String cmd[];
		Stack stack = new Stack();
		
		for(int i=0;i<testcase;i++)
		{
			cmd = br.readLine().split(" ");
			
			if(cmd[0].equals("push"))
			{
				stack.push(Integer.parseInt(cmd[1]));
			}else if(cmd[0].equals("pop"))
			{
				System.out.println(stack.pop());
			}else if(cmd[0].equals("size"))
			{
				System.out.println(stack.size());
			}else if(cmd[0].equals("empty"))
			{
				System.out.println(stack.empty());
			}else if(cmd[0].equals("top"))
			{
				System.out.println(stack.top());
			}
		}
	}
}
