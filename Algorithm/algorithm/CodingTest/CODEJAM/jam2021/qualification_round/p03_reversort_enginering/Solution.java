package CODEJAM.jam2021.qualification_round.p03_reversort_enginering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	static List<List<Integer>> p;
	
	static int findMinPosition(int i, int n, List<Integer> L)
	{
		int minValue = L.get(i);
		int minIndex = i;
		
		for(int start = i+1; start < n; start++)
		{
			if(L.get(start)<minValue)
			{
				minValue = L.get(start);
				minIndex = start;
			}
		}
		return minIndex;
	}
	
	static void reverse(int i, int j, List<Integer> L)
	{
		while(i!=j && i<j)
		{
			int tmp = L.get(i);
			L.set(i, L.get(j));
			L.set(j, tmp);
			
			i++;
			j--;
		}
	}
	
	static int reverSort(List<Integer> L)
	{
		int n = L.size();
		int j = 0;		
		int cost = 0;
		
		for(int i=0; i<n-1; i++)
		{
			j = findMinPosition(i, n, L);
			reverse(i, j, L);
			cost += (j-i+1);			
		}
		
		return cost;
	}
	
	static void findPermutation(int n, int c, List<Integer> list, boolean[] chosen)
	{
		if(list.size()==n)
		{
			p.add(new ArrayList<Integer>(list));
		}
		else
		{
			for(int i=1; i<=n; i++)
			{
				if(chosen[i])
				{
					continue;
				}
				
				chosen[i] = true;
				list.add(i);
		
				findPermutation(n, c, list, chosen);
				
				chosen[i] = false;
				list.remove(list.size()-1);
			}
		}
	}
	
	static List<Integer> solution(int n, int c)
	{
		p = new ArrayList<List<Integer>>();
		List<Integer> list = new ArrayList<Integer>();
		boolean[] chosen = new boolean[n+1];
		int cost = 0;
		findPermutation(n, c, list, chosen);
		
		for(List<Integer> li : p)
		{
			List<Integer> origin_list = new ArrayList<Integer>(li);
			cost = reverSort(li);
			
			if(cost == c)
			{
				return origin_list; 
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		int n = 0;
		int c = 0;
		List<Integer> result = null;
		String[] line = null;
		
		for(int i=0; i<tc; i++)
		{
			line = br.readLine().split(" ");
			
			n = Integer.parseInt(line[0]);
			c = Integer.parseInt(line[1]);
			result = solution(n, c);
			
			System.out.printf("Case #%d: ",i+1);
			if(result==null)
			{
				System.out.printf("IMPOSSIBLE\n");
			}
			else
			{
				for(Integer item : result)
				{
					System.out.printf("%d ", item);
				}
				System.out.printf("\n");
			}
		}
	}
}
