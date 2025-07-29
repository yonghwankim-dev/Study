package BOJ.BOJ_10866;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	static class Deque
	{
		private List<Integer> deque;
		
		Deque()
		{
			deque = new ArrayList<Integer>();
		}
		
		void push_front(int x)
		{
			deque.add(0, x);
		}
		void push_back(int x)
		{
			deque.add(x);
		}
		int pop_front()
		{
			if(empty()==1)
			{
				return -1;
			}
			return deque.remove(0);
		}
		int pop_back()
		{
			if(empty()==1)
			{
				return -1;
			}
			return deque.remove(size()-1);
		}
		int size()
		{
			return deque.size();
		}
		int empty()
		{
			return (size()!=0 ? 0 : 1);
		}
		int front()
		{
			if(empty()==1)
			{
				return -1;
			}
			return deque.get(0);
		}
		int back()
		{
			if(empty()==1)
			{
				return -1;
			}
			return deque.get(size()-1);
		}
		
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		Deque d = new Deque();
		for(int i=0;i<n;i++)
		{
			String[] cmd = br.readLine().split(" ");
			
			switch(cmd[0])
			{
			case "push_front":
				d.push_front(Integer.parseInt(cmd[1]));
				break;
			case "push_back":
				d.push_back(Integer.parseInt(cmd[1]));
				break;
			case "pop_front":
				System.out.println(d.pop_front());
				break;
			case "pop_back":
				System.out.println(d.pop_back());
				break;
			case "size":
				System.out.println(d.size());
				break;
			case "empty":
				System.out.println(d.empty());
				break;
			case "front":
				System.out.println(d.front());
				break;
			case "back":
				System.out.println(d.back());
				break;
				
			}
		}
		
	}
}
