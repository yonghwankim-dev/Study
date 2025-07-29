package BOJ.BOJ_1655;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Queue<Integer> pq = new PriorityQueue<>(Integer::compareTo);
		for(int i = 0; i < n; i++){
			pq.offer(Integer.parseInt(br.readLine()));
			Integer midInteger = findMidinumVal(pq);
			System.out.println(midInteger);
		}
	}

	private static Integer findMidinumVal(Queue<Integer> pq) {
		// �߰��� = size()-1 / 2;
		Queue<Integer> copy = new PriorityQueue<>(pq);
		int midIdx = (copy.size() - 1) / 2;
		int i = 0;

		while(!copy.isEmpty()){
			int element = copy.poll();
			if(i == midIdx){
				return element;
			}
			i++;
		}
		return null;
	}
}
