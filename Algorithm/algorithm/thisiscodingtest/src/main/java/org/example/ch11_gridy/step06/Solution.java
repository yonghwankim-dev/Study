package org.example.ch11_gridy.step06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
	public int solution(int[] food_times, long k) {
		// 전체 음식을 먹을 시간이 k보다 짧거나 전체 음식을 먹을 시간이 k와 같아서 남긴 음식이 없다면 -1 반환
		if(sum(food_times) <= k){
			return -1;
		}

		// 시간이 작은 음식부터 빼야 하므로 우선순위 큐 사용
		PriorityQueue<int[]> pq = new PriorityQueue(Comparator.comparingInt(o->((int[])o)[0]));

		for(int i = 0; i < food_times.length; i++){
			// (음식 시간, 음식 번호) 형태로 우선순위 큐에 삽입
			pq.offer(new int[]{food_times[i], i + 1});
		}

		long sum = 0; // 먹기 위해 사용한 시간
		long previous = 0; // 직전에 다 먹은 음식 시간
		int remainFood = food_times.length; // 남은 음식 개수
		// (sum + (현재 음식 시간 - 이전 음식 시간) * 현재 음식 개수)와 k 비교
		while(sum + (pq.peek()[0] - previous) * remainFood <= k){
			long now = pq.poll()[0];
			sum += (now - previous) * remainFood;
			remainFood--; // 다 먹은 음식 제외
			previous = now; // 이전 음식 시간 재설정
		}


		// 남은 음식 중에서 몇번째 음식인지 확인하여 출력
		// 음식 번호를 기준으로 정렬
		int[] result = pq.stream()
			.sorted(Comparator.comparingInt(o -> o[1]))
			.mapToInt(value -> value[1])
			.toArray();
		return result[(int)((k - sum) % remainFood)];
	}

	private Long sum(int[] food_times){
		return Arrays.stream(food_times)
			.mapToLong(Long::valueOf)
			.sum();
	}
}
