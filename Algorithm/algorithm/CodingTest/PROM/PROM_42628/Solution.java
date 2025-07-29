package PROM.PROM_42628;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
	public static int[]	solution(String[] operations)
	{
		int[] answer = new int[2];
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		
		for(String op : operations)
		{
			String[] str = op.split(" ");
			String first = str[0];
			String second = str[1];
			
			if(first.equals("I"))
			{
				int add_value = Integer.parseInt(second);
				pq.add(add_value);
			}
			else if(first.equals("D"))
			{
				if(pq.isEmpty())
				{
					continue;
				}
				
				if(second.equals("1"))
				{
					// max
					int max_val = pq.stream().mapToInt(i->i).max().getAsInt();
					pq.remove(max_val);
				}
				else if(second.equals("-1"))
				{
					// min
					pq.poll();
				}
			}
		}
		
		if(pq.isEmpty())
		{
			return answer;
		}
		else
		{
			int max_val = pq.stream().mapToInt(i->i).max().getAsInt();
			answer[0] = max_val;
			answer[1] = pq.peek();
		}
		
		return answer;
	}
	
	public static void main(String[] args) {
		String operations[] = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};
		String operations2[] = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
		
		System.out.println(Arrays.toString(solution(operations)));
		System.out.println(Arrays.toString(solution(operations2)));
		
	}
}
