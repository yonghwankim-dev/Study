package PROM.PROM_12939;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    public static String solution(String s) {
    	
    	List<Integer> list = Arrays.asList(s.split(" "))
    							.stream()
    							.map(Integer::parseInt)
    							.collect(Collectors.toList());

        return String.format("%d %d", Collections.min(list),Collections.max(list));
    }
    public static void main(String args[])
    {
    	System.out.println(solution("1 2 3 4"));
    	System.out.println(solution("-1 -2 -3 -4"));
    	System.out.println(solution("-1 -1"));
    }
}
