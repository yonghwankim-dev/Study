package PROM.PROM_17681;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {
	static Map<Integer, String> map = new HashMap<Integer, String>() {
		{
			put(0, " ");
			put(1, "#");
		}
	};

	public static String decode(int n, int num) {
		String s = "";

		for (int i = n - 1; i >= 0; i--) {
			s = map.get(num % 2) + s;
			num /= 2;
		}

		return s;
	}

	public static String[] merge(int n, String[] map1, String[] map2) {
		String[] answer = new String[n];
		StringBuilder sb;
		for (int i = 0; i < n; i++) {
			sb = new StringBuilder();
			for (int j = 0; j < n; j++) {
				if (map1[i].charAt(j) == '#' || map2[i].charAt(j) == '#') {
					sb.append("#");
				} else {
					sb.append(" ");
				}
			}
			answer[i] = sb.toString();
		}
		return answer;
	}

	public static String[] solution(int n, int[] arr1, int[] arr2) {
		String[] answer = new String[n];
		String[] map1 = new String[n];
		String[] map2 = new String[n];

		for (int i = 0; i < n; i++) {
			map1[i] = decode(n, arr1[i]);
			map2[i] = decode(n, arr2[i]);
		}
		answer = merge(n, map1, map2);

		return answer;
	}

	public static String[] solution2(int n, int[] arr1, int[] arr2) {
		String[] result = new String[n];
		for (int i = 0; i < n; i++) {
			result[i] = Integer.toBinaryString(arr1[i] | arr2[i]);
		}

		for (int i = 0; i < n; i++) {
			result[i] = String.format("%" + n + "s", result[i]);
			result[i] = result[i].replaceAll("1", "#");
			result[i] = result[i].replaceAll("0", " ");
		}

		return result;
	}

	public static void main(String[] args) {
		int n = 5;
		int[] arr1 = { 9, 20, 28, 18, 11 };
		int[] arr2 = { 14, 1, 21, 17, 28 };

		System.out.println(Arrays.toString(solution2(n, arr1, arr2)));
	}
}
