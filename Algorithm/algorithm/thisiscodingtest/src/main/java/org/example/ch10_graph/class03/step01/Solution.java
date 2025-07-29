package org.example.ch10_graph.class03.step01;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 팀 결성
 */
public class Solution {
	public static void main(String[] args) {
		int n = 7;
		int m = 8;

		String[] inputs = {
			"0 1 3",
			"1 1 7",
			"0 7 6",
			"1 7 1",
			"0 3 7",
			"0 4 2",
			"0 1 1",
			"1 1 1"
		};
		int[] parent = new int[n + 1];
		for (int i = 0; i <= n; i++){
			parent[i] = i;
		}

		List<String> answer = new ArrayList<>();
		for (int i = 0; i < m; i++){
			StringTokenizer st = new StringTokenizer(inputs[i]);
			int op = Integer.parseInt(st.nextToken());
			int src = Integer.parseInt(st.nextToken());
			int dst = Integer.parseInt(st.nextToken());

			if (op == 0){
				union_parent(parent, src, dst);
			}else if (op == 1){
				if(find_parent(parent, src) == find_parent(parent, dst)){
					answer.add("YES");
				}else{
					answer.add("NO");
				}
			}
		}
		answer.forEach(System.out::println);
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

	private static int find_parent(int[] parent, int i) {
		if (parent[i] != i){
			parent[i] = find_parent(parent, parent[i]);
		}
		return parent[i];
	}
}
