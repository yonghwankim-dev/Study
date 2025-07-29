package PROM.PROM_12954;

import java.util.Arrays;

public class Solution {

    public static long[] solution(int x, int n) {
        long[] answer = new long[n];
        
        answer[0] = x;
        for(int i=1;i<n;i++)
        {
        	answer[i] = answer[i-1]+x; 
        }
        
        return answer;
    }

	public static void main(String[] args)
	{		
		System.out.println(Arrays.toString(solution(2, 5)));
	}
}
