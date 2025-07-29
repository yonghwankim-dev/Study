package LEETCODE.LEETCODE_2699;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
	public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {

		// source에 해당하는 번호를 queue에 삽입
		// queue가 빌때까지 수행
		// queue에서 poll하여 거리가 -1이면 1로 변경
		// queue에서 poll하여 거리가 -1이 아니면 skip
		// source -> destination 까지 distance가 target보다 작으면 source에서 가장 가까운 경로에 대하여 target - 거리 총합의 값을 경로에 누적한다

		Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
		while (!queue.isEmpty()){
			int[] node = queue.poll();
			int number = node[0];
			int dist = node[1];
		}
		return null;
	}
}
