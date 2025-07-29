package BOJ.BOJ_1339;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		int answer = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());

		// 문자열 입력
		String[] words = new String[n];
		for (int i = 0; i < n; i++){
			words[i] = br.readLine();
		}

		// 각각의 문자열을 대상으로 자릿수마다 가중치 부여
		int[] alpha = new int[26];
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			String word = words[i];
			int weight = (int)Math.pow(10, word.length() - 1);

			for (char c : word.toCharArray()) {
				int idx = c - 65;
				alpha[idx] += weight;
				weight /= 10;
			}
		}
		Arrays.sort(alpha);
		int num = 9;
		for (int i = alpha.length - 1; i >= 0; i--){
			if (alpha[i] != 0){
				answer += (alpha[i] * num);
				num--;
			}
		}
		System.out.println(answer);
	}
}
