package BOJ.BOJ_2108;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static int findMode(List<Integer> list)
	{
		Map<Integer, ArrayList<Integer>> mrv = new LinkedHashMap<>();
		int maxRep = 0;
		
		mrv.put(1, new ArrayList<Integer>());
		
		for(int i=0, rep=1;i<list.size();i++)
		{
			if(mrv.get(rep).contains(list.get(i)))
			{
				continue;
			}
			rep=1;
			// list는 정렬된 상태이므로 같은수가 연속되지 않으면 더이상 볼 필요없음
			for(int j=i+1;j<list.size();j++)
			{
				if(!list.get(i).equals(list.get(j)))
				{
					break;
				}
				rep++;
			}
			maxRep = rep > maxRep ? rep : maxRep;
			if(!mrv.containsKey(rep))
			{
				mrv.put(rep, new ArrayList<Integer>());
				mrv.get(rep).add(list.get(i));
			}
			else
			{
				mrv.get(rep).add(list.get(i));
			}
		}
		
		Integer[] result = mrv.get(maxRep).toArray(new Integer[0]);
		Arrays.sort(result);
		
		if(result.length==1)
		{
			maxRep = result[0];
		}
		else
		{
			maxRep = result[1];
		}
		
		return maxRep;
	}
	public static void solution(List<Integer> list)
	{
		Collections.sort(list);
		
		// 산술평균
		int average = 0;
		for(int i=0; i<list.size(); i++)
		{
			average += list.get(i);
		}
		average = (int) Math.round(average / (double)list.size());
		
		// 중앙값
		int middle = list.get(list.size()/2);
		
		// 최빈값
		int mode = findMode(list);
		
		// 범위
		int range = list.get(list.size()-1) - list.get(0);
		
		
		System.out.println(average);
		System.out.println(middle);
		System.out.println(mode);
		System.out.println(range);
		
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<n;i++)
		{
			list.add(Integer.parseInt(br.readLine()));
		}
		solution(list);
		
	}
}
