package PROM.PROM_87389;

import java.util.stream.IntStream;

public class Solution{
	
    public static int solution(int n) {
        for(int x=2;x<n;x++)
        {
        	if(n%x==1)
        	{
        		return x;
        	}
        }
        return 0; 
    }
    public int solution2(int n) {
        return IntStream.range(2, n).filter(i -> n % i == 1).findFirst().orElse(0);
    }
    
	public static void main(String args[])
	{
		System.out.println(solution(10));
		System.out.println(solution(12));
	}
}
