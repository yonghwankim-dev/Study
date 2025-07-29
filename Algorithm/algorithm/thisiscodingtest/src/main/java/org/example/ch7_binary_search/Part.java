package org.example.ch7_binary_search;

import java.util.Arrays;

public class Part {
	public String solution(int[] parts, int[] targets){
		Arrays.sort(parts);
		StringBuilder sb = new StringBuilder();
		for (int target : targets){
			String result = search(parts, target, 0, parts.length - 1);
			sb.append(result).append(" ");
		}
		return sb.toString().trim();
	}

	private String search(int[] parts, int target, int start, int end){
		while (start <= end){
			int mid = (start + end) / 2;
			if (parts[mid] == target){
				return "yes";
			}else if (parts[mid] > target){
				end = mid - 1;
			}else{
				start = mid + 1;
			}
		}
		return "no";
	}
}
