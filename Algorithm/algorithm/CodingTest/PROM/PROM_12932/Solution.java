package PROM.PROM_12932;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static int[] solution(long n) {
        List<Integer> list = new ArrayList<>();
        
        long temp = n;
        int i=0;
        while(temp!=0)
        {
        	int nmg = (int) (temp%10);
        	temp = temp/10;
        	list.add(nmg);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

	public static void main(String args[]) {
		long n = 12345;
		System.out.println(Arrays.toString(solution(n)));
	}
}
