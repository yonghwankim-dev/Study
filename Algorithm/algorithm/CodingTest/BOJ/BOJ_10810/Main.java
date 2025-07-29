package BOJ.BOJ_10810;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] split =  br.readLine().split(" ");
		int n = Integer.parseInt(split[0]);
		int m = Integer.parseInt(split[1]);
		int[] arr = new int[n];

		for (int i = 0; i < m; i++){
			split = br.readLine().split(" ");
			int start = Integer.parseInt(split[0]);
			int end = Integer.parseInt(split[1]);
			int k = Integer.parseInt(split[2]);

			for(int j = start; j <= end; j++){
				arr[j - 1] = k;
			}
		}
		List<String> strings = Arrays.stream(arr)
			.mapToObj(String::valueOf)
			.collect(Collectors.toList());
		System.out.println(String.join(" ",strings));
	}
}
