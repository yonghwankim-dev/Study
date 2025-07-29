package PROM.PROM_86491;

import java.util.Arrays;

public class Solution {
	
	public static int solution(int[][] sizes) {
        
        int[] big = new int[sizes.length];
        int[] small = new int[sizes.length];
        
        
        for(int i=0;i<sizes.length;i++)
        {
        	int width = sizes[i][0];
        	int height = sizes[i][1];
        	
        	if(width>height)
        	{
        		big[i] = width;
        		small[i] = height;
        	}
        	else
        	{
        		big[i] = height;
        		small[i] = width;
        	}
        }
        
        
        return Arrays.stream(big).max().getAsInt() * Arrays.stream(small).max().getAsInt();
    }
	
	public static void main(String args[])
	{
		int[][] arr = {{60,50},{30,70},{60,30},{80,40}};
		solution(arr);
	}
}
