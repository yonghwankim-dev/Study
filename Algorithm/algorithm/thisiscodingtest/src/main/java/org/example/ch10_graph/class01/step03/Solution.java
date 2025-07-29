package org.example.ch10_graph.class01.step03;

import java.util.StringTokenizer;

/**
 * 서로소 집합을 이용한 사이클 판별
 */
public class Solution {
	public static void main(String[] args) {
		int n = 3;
		int m = 3;
		int[] parent = new int[n + 1];

		// parent에서 부모를 자기자신으로 초기화
		for (int i = 1; i <= n; i++){
			parent[i] = i;
		}
		boolean cycle = false;
		String[] inputs = {
			"1 2",
			"1 3",
			"2 3"
		};
		// 2. 모든 간선에 대하여 1번 과정을 반복
		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			// 1. 두 노드의 루트 노드를 확인
			// 1.1 루트 노드가 다르면 두 노드에 대하여 union 연산 수행
			if (find_parent(parent, src) != find_parent(parent, dst)){
				union_parent(parent, src, dst);
			}else{ // 1.2 루트 노드가 같으면 cyle 발생한 것으로 판명
				cycle = true;
				break;
			}
		}
		if (cycle){
			System.out.println("싸이클이 발생했습니다.");
		}else{
			System.out.println("싸이클이 발생하지 않았습니다");
		}
	}

	private static int find_parent(int[] parent, int i) {
		if (parent[i] != i){
			parent[i] = find_parent(parent, parent[i]);
		}
		return parent[i];
	}

	private static void union_parent(int[] parent, int src, int dst) {
		int a = find_parent(parent, src);
		int b = find_parent(parent, dst);
		if (a < b){
			parent[b] = a;
		}else{
			parent[a] = b;
		}
	}
}
