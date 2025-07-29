package BOJ.BOJ_1715;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Queue<Integer> queue = new PriorityQueue<>();
		for (int i = 0; i < n; i++){
			queue.offer(Integer.parseInt(br.readLine()));
		}
		int answer = solution(queue, n);
		System.out.println(answer);
	}

	private static int solution(Queue<Integer> queue, int n) {
		int answer = 0;
		while (queue.size() > 1){
			int a = queue.poll();
			int b = queue.poll();
			answer += (a + b);
			queue.offer(a + b);
		}
		return answer;
	}
}
