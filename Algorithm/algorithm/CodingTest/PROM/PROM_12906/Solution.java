package PROM.PROM_12906;

import java.util.ArrayList;
import java.util.List;

public class Solution{

	public static int[] solution(int []arr) {
		int prevNum = -1;
		List<Integer> list = new ArrayList<Integer>();

		for(int num : arr){
			if(prevNum != num){
				list.add(num);
			}
			prevNum = num;
		}

		return list.stream().mapToInt(Integer::intValue).toArray();
	}
}
