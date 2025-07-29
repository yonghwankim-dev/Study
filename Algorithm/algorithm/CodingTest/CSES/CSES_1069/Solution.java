package CSES.CSES_1069;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Solution {
	public int solution(String s) {
		Map<String, Integer> counter = new HashMap<String, Integer>();
		counter.put("A", 0);
		counter.put("C", 0);
		counter.put("G", 0);
		counter.put("T", 0);
		
		int answer = 0;	// 가장긴 dna 길이
		String[] seq = s.split("");
		String prev = seq[0];
		
		for(String dna : seq) {
			if(!dna.equals(prev)) {
				answer = Math.max(answer, counter.get(prev)); // 이전 dna까지의 반복횟수와 최대길이와 비교하여 최대값 저장
				prev = dna;
				counter.put(dna, 0);
			}
			counter.put(dna, counter.get(dna) + 1);	// 반복횟수 1 증가
		}
		
		answer = Math.max(answer, counter.get(prev));
		
		return answer;
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String seq = br.readLine();
		
		System.out.println(new Solution().solution(seq));
	}
}
