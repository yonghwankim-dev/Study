package PROM.PROM_17682;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
	
	public static int getSum(int[] arr)
	{
		int sum=0;
		for(int i=0;i<arr.length;i++)
		{
			sum+=arr[i];
		}
		return sum;
	}
	
	public static int solution(String dartResult) {
		int[] answer = new int[3];
	
		Map<String, Integer> bonus = new HashMap<String, Integer>(){{
			put("S",1);
			put("D",2);
			put("T",3);
		}};
		
		Map<String, Integer> opt_map = new HashMap<String,Integer>(){{
			put("*",2);
			put("#",-1);
			put("",1);
		}};
		
		// "1D2S#10S" -> 1D 2S# 10S 
		String regex = "(\\d+)([SDT])([\\*#]?)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(dartResult);
		
		int i=0;
		while(matcher.find())
		{	
			int score = Integer.parseInt(matcher.group(1));	// 점수
			String area = matcher.group(2);	// 영역
			String opt = matcher.group(3);	// 점수
	
			score = (int) Math.pow(score, bonus.get(area)) * opt_map.get(opt);
			
			
			if(opt.equals("*") && i>0)
			{
				answer[i-1] *= 2;
			}
			
			answer[i] = score;
			i++;	
		}
		
		return getSum(answer);
    }
}
