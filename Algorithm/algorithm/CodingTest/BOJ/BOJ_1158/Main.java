package BOJ.BOJ_1158;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Main {
	public static List<Integer> solution(int N, int K)
	{
		
		List<Integer> answer = new ArrayList<>();
		Queue<Integer> queue = new LinkedList<>();
		
		for(int i=1;i<=N;i++)
		{
			queue.add(i);
		}
	
		int count = 0;
		while(queue.size()!=1)
		{
			count++;
			if(count!=K)
			{
				queue.add(queue.poll());
			}
			else
			{
				answer.add(queue.poll());
				count=0;
			}
		}
		answer.add(queue.poll());
		return answer;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String strs[] = br.readLine().split(" ");
		int N = Integer.parseInt(strs[0]);
		int K = Integer.parseInt(strs[1]);
		
		List<Integer> answer = solution(N, K);
		
		sb.append("<");
		for(int i=0;i<answer.size();i++)
		{
			if(i==answer.size()-1)
			{
				sb.append(answer.get(i));
			}
			else 
			{
				sb.append(answer.get(i)+", ");
			}
		}
		sb.append(">");
		System.out.println(sb);
		
		
	}
}
