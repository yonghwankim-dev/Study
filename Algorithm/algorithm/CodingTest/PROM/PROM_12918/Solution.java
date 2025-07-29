package PROM.PROM_12918;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution{
	
    public int[] solution(int[] arr, int divisor) {
        List<Integer> list = new ArrayList<>();
        
        Arrays.sort(arr);
        
        for(int i=0;i<arr.length;i++)
        {
        	if(arr[i]%divisor==0)
        	{
        		list.add(arr[i]);
        	}
        }
        
        if(list.size()==0)
        {
        	list.add(-1);
        }
         
        return list.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public int[] solution2(int[] array, int divisor) {
        return Arrays.stream(array).filter(factor -> factor % divisor == 0).toArray();
    }
    
	
}
