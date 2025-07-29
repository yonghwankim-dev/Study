package PROM.PROM_12933;

import java.util.Arrays;
import java.util.Collections;

public class Solution {

    public static long solution(long n) {
        String s = String.valueOf(n);
        int len = s.length();
        long[] arr = new long[len];
        StringBuilder sb = new StringBuilder();
        
        for(int i=0;i<len;i++)
        {
        	arr[i] = Long.parseLong(s.charAt(i)+"");
        }
        
        Arrays.sort(arr);
        for(int i=0;i<len;i++)
        {
        	sb.append(arr[i]);
        }
        
        return Long.parseLong(sb.reverse().toString());
    }
    
    public static int reverseInt(int n){

        String str = Integer.toString(n);
        String[] strs = str.split("");
        StringBuilder sb = new StringBuilder();
        
        Arrays.sort(strs,Collections.reverseOrder());
        Arrays.stream(strs).forEach(item->sb.append(item));
        
		return Integer.parseInt(sb.toString());
    }

    
	public static void main(String[] args)
	{
		System.out.println(reverseInt(1235489));
	}
}
