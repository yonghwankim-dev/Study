package PROM.PROM_12917;

import java.util.Arrays;

public class Solution {
    public static String solution(String s) {
        
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        
        for(int i=0;i<n-1;i++)
        {
        	int max_val = i;
        	for(int j=i+1;j<n;j++)
        	{
        		if(sb.charAt(max_val)<sb.charAt(j))
        		{
        			max_val = j;
        		}
        	}
        	char temp = sb.charAt(max_val);
        	sb.setCharAt(max_val, sb.charAt(i));
        	sb.setCharAt(i, temp);	
        }
        
        return sb.toString();
    }
    
    public String solution2(String str){
        char[] sol = str.toCharArray();
        Arrays.sort(sol);
        return new StringBuilder(new String(sol)).reverse().toString();
    }

    
    public static void main(String[] args)
    {
    	System.out.println(solution("Zbcdefg"));
    }
}
