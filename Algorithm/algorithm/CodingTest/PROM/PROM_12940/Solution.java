package PROM.PROM_12940;

import java.util.Arrays;

public class Solution {
	public static int gcd(int n, int m)
	{
		int temp, nmg;
		
		// n에 큰값을 위치시킴
		if(n<m)
		{
			temp = n;
			n = m;
			m = temp;
		}
		
		while(m!=0)
		{
			nmg = n%m;
			n = m;
			m = nmg;
		}
		return n;
	}
	
    public static int[] solution(int n, int m) {
        int[] answer = new int[2];
        
        int gcdVal = gcd(n,m);
        int lcdVal = (n*m)/gcdVal;
        
        answer[0] = gcdVal;
        answer[1] = lcdVal;
        
        return answer;
    }
	
	public static void main(String[] args)
	{
		System.out.println(Arrays.toString(solution(3, 12)));
		System.out.println(Arrays.toString(solution(2,5)));
	}
}
