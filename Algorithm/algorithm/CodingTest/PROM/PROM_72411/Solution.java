package PROM.PROM_72411;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution{
	
	public void combination(char[] arr, boolean[] chosen, int depth, int n, int r, Map<String, Integer> map){
		if(r == 0)
		{
			String result = "";
			for(int i = 0; i < n; i++)
			{
				if(chosen[i])
				{
					result += arr[i];
				}
			}
			
			char[] tempArray = result.toCharArray();
			Arrays.sort(tempArray);
			result = new String(tempArray);
			
			if(map.containsKey(result))
			{
				map.put(result, map.get(result) + 1);
			}
			else
			{
				map.put(result, 1);
			}
			
			return;
		}
		
		if(depth == n)
		{
			return;
		}
		
		// 특정 원소를 포함하고 뽑는 경우
		chosen[depth] = true;
		combination(arr, chosen, depth + 1, n, r - 1, map);
		
		// 특정 원소를 포함하지 않고 뽑는 경우
		chosen[depth] = false;
		combination(arr, chosen, depth + 1, n, r, map);
	}
		
    public String[] solution(String[] orders, int[] course) {
    	List<String> list = new ArrayList<>();
    	String[] answer = null;
    	for(int i = 0; i < course.length; i++)
    	{
    		int maxVal = 0;
        	String result = "";
        	Map<String, Integer> map = new HashMap<String, Integer>();
        	
    		for(String order : orders)
        	{
        		char[] arr = order.toCharArray();
        		int n = arr.length;
        		boolean[] chosen = new boolean[n];
        		combination(arr, chosen, 0, n, course[i], map);	// n개중에서 r개 뽑기	
        	}
    		
        	for(String key : map.keySet())
        	{
        		if(map.get(key) > maxVal)
        		{
        			maxVal = map.get(key);
        		}
        	}
        	for(String key : map.keySet())
        	{
        		if(map.get(key) == maxVal && maxVal >= 2)
        		{
        			list.add(key);
        		}
        	}
    	}
    	list = list.stream().sorted().collect(Collectors.toList());
    	answer = list.toArray(new String[0]);
        return answer;
    }
}
