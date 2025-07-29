package PROM.PROM_12935;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static int[] solution(int[] arr) {
    	int n = arr.length;
    	
    	int minIdx = 0;
    	List<Integer> list = new ArrayList<Integer>();
    	for(int i=0;i<arr.length;i++)
    	{
    		if(arr[i]<arr[minIdx])
    		{
    			minIdx = i;
    		}
    		list.add(arr[i]);
    	}
    	
    	list.remove(minIdx);
    	
    	if(list.size()==0)
    	{
    		list.add(-1);
    	}
        return list.stream().mapToInt(Integer::intValue).toArray(); 
    }

    public int[] solution2(int[] arr) {
        if (arr.length <= 1) return new int[]{ -1 };
        int min = Arrays.stream(arr).min().getAsInt();
        return Arrays.stream(arr).filter(i -> i != min).toArray();
    }
    
	public static void main(String[] args)
	{
		int[] arr = {10};
		System.out.println(Arrays.toString(solution(arr)));
	}
}
