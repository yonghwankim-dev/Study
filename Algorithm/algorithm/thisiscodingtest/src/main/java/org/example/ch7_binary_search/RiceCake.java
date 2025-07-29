package org.example.ch7_binary_search;

import java.util.Arrays;

import org.example.ch4_imple.Solution2;

public class RiceCake {
	public int solution(int n, int m, int[] cakes){
		Arrays.sort(cakes);
		int answer = 0;
		int start = 0;
		int end = cakes[n - 1];
		while(start <= end){
			int mid = (start + end) / 2;
			System.out.printf("start = %d, end = %d, mid = %s%n", start, end, mid);
			int sliceLen = slice(mid, cakes);
			System.out.println("sliceLen = " + sliceLen);
			if(sliceLen == m){
				return mid;
			}else if(sliceLen > m) {
				answer = mid;
				start = mid + 1;
			}else if (sliceLen < m){
				end = mid - 1;
			}
		}
		return answer;
	}

	private int slice(int h, int[] cakes){
		int result = 0;
		for (int cake : cakes){
			if (cake > h){
				result += (cake - h);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int answer = new RiceCake().solution(4, 6, new int[] {19, 15, 10, 17});
		System.out.println(answer);
	}
}
