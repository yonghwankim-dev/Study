package BOJ.BOJ_2212;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		int[] sensors = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		int answer = solution(sensors, n, k);
		System.out.println(answer);
	}

	private static int solution(int[] sensors, int n, int k) {
		// k >= n 이면 0 반환, k>=n이면 센서 사이의 거리는 무조건 0이 된다
		if (k >= n){
			return 0;
		}
		// 센서를 기준으로 오름차순 정렬
		Arrays.sort(sensors);

		// 각 센서 사이의 거리 차이 배열 생성
		List<Integer> diff = new ArrayList<>();
		for (int i = 0; i < n - 1; i++){
			diff.add(Math.abs(sensors[i] - sensors[i + 1]));
		}

		// 차이 배열을 내림차순 정렬
		Collections.sort(diff, Comparator.reverseOrder());

		// 차이 배열에서 k-1 ~ 마지막까지 합계을 계산
		int answer = 0;
		for (int i = k - 1; i < diff.size(); i++){
			answer += diff.get(i);
		}
		return answer;
	}
}
