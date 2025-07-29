package BOJ.BOJ_11047;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static int solution(int[] values, int k){
		int n = values.length;
		int i = n - 1;
		int result = 0;
		while(k != 0){
			int count = k / values[i];
			k = k % values[i];
			result += count;
			i--;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String[] text = br.readLine().split(" ");
		int n = Integer.parseInt(text[0]);
		int k = Integer.parseInt(text[1]);
		int[] values = new int[n];
		for (int i = 0; i < n; i++){
			values[i] = Integer.parseInt(br.readLine());
		}
		int result = solution(values, k);
		System.out.println(result);
	}
}
