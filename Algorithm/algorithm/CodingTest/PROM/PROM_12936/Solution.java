package PROM.PROM_12936;

import java.util.Arrays;
import java.util.stream.IntStream;

class Solution {
	static long cnt;
	static long r;
	static long k;

	static int[] answer;
	static int[] arr;
	static int n;

	public static int[] solution(int n, long k) {
		cnt = 0;
		r   = n;
		Solution.k = k;
		arr = IntStream.rangeClosed(1, n).toArray();
		Solution.n = n;
		permutation(0);
		return answer;
	}

	private static void permutation(int depth){
		if(depth == r){
			cnt++;
			if(cnt == k){
				answer = Arrays.copyOf(arr, n);
			}
		}

		for(int i = depth; i < n; i++){
			swap(i, depth);
			permutation(depth + 1);
			swap(i, depth);
		}
	}

	private static void swap(int from, int to){
		int temp = arr[from];
		arr[from] = arr[to];
		arr[to] = temp;
	}
}
