package PROM.PROM_12912;


import java.util.stream.LongStream;


public class Solution {
	
	public static long solution(long a, long b) {
        return LongStream.range(Math.min(a, b), Math.max(a, b)+1).sum();
    }
    
    public static void main(String[] args)
    {
    	System.out.println(solution(5,3));
    }
}
