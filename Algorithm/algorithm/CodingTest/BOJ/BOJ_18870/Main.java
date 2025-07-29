package BOJ.BOJ_18870;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Main {
	
	public static String solution(int[] origin_arr)
	{
		Map<Integer, Integer> map = new HashMap<>();
		int[] sorted_arr = new int[origin_arr.length];
		StringBuilder sb = new StringBuilder();
		
		System.arraycopy(origin_arr, 0, sorted_arr, 0, origin_arr.length);
		
		Arrays.sort(sorted_arr);
		
		int point = 0;
		for(int i=0;i<sorted_arr.length;i++)
		{
			if(!map.containsKey(sorted_arr[i]))
			{
				map.put(sorted_arr[i], point);
				point++;
			}
		}
		
		for(int i=0;i<origin_arr.length;i++)
		{
			sb.append(map.get(origin_arr[i]) + " ");
		}
		
		return sb.toString().trim();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		String[] str = br.readLine().split(" ");
		int[] origin_arr = Arrays.stream(str).mapToInt(Integer::parseInt).toArray();
		
		System.out.println(solution(origin_arr));
		
	}
}
