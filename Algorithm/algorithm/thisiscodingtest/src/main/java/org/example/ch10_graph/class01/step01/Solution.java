package org.example.ch10_graph.class01.step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {
		// 노드 개수 및 간선 개수 입력
		int n = 6;
		int m = 4;
		int[] parent = new int[n + 1];

		// 부모 테이블에서 부모 값을 자기 자신을 초기화
		for (int i = 1; i <= n; i++){
			parent[i] = i;
		}

		// 간선 개수만큼 정보를 입력받아 union 연산 수행
		String[] inputs = {
			"1 4",
			"2 3",
			"2 4",
			"5 6"
		};
		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());
			union_parent(parent, src, dst);
		}

		// 각 원소가 속한 집합 출력
		for (int i = 1; i <= n; i++){
			System.out.print(find_parent(parent, i) + " ");
		}
		System.out.println();

		// 부모 테이블 내용 출력
		for (int i = 1; i <= n; i++){
			System.out.print(parent[i] + " ");
		}
	}

	// 두 원소가 속한 집합을 합치기
	private static void union_parent(int[] parent, int src, int dst){
		int a = find_parent(parent, src);
		int b = find_parent(parent, dst);
		if (a < b){
			parent[b] = a;
		}else{
			parent[a] = b;
		}
	}

	private static int find_parent(int[] parent, int i) {
		if (parent[i] == i){
			return i;
		}
		return find_parent(parent, parent[i]);
	}
}
