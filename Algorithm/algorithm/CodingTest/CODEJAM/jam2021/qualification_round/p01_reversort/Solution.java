package CODEJAM.jam2021.qualification_round.p01_reversort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solution {

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
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());
		int n = 0;
		List<Integer> L = null;
		String[] sp = null;
		
		for(int i=0; i<tc; i++)
		{
			n = Integer.parseInt(br.readLine());
			sp = br.readLine().split(" ");
			L = new ArrayList<Integer>();
			
			for(int j = 0; j<sp.length; j++)
			{
				L.add(Integer.parseInt(sp[j]));
			}
			
			System.out.printf("Case #%d: %d\n",i+1,reverSort(L));
		}
	}
}
