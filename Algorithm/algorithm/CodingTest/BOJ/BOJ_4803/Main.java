package BOJ.BOJ_4803;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		while (true){
			int tc = 1;
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			if (n == 0 && m == 0){
				break;
			}

			int[] parent = new int[n + 1];
			for (int i = 1; i <= n; i++){
				parent[i] = i;
			}
			for (int i = 0; i < m; i++){
				st = new StringTokenizer(br.readLine());
				int src = Integer.parseInt(st.nextToken());
				int dst = Integer.parseInt(st.nextToken());
				union_parent(parent, src, dst);
			}

			Set<Integer> treeSet = new HashSet<>();
			for (int i = 1; i <= n; i++){
				int a = find_parent(parent, i);
				if (a > 0){
					treeSet.add(a);
				}
			}
			if (treeSet.isEmpty()){
				sb.append(String.format("Case %d: No trees.%n", tc));
			}else if (treeSet.size() == 1){
				sb.append(String.format("Case %d: There is one tree.%n", tc));
			}else {
				sb.append(String.format("Case %d: A forest of %d trees.%n", tc, treeSet.size()));
			}
			tc++;
		}
		System.out.print(sb.toString().trim());
	}

	private static int find_parent(int[] parent, int i) {
		if (parent[i] == i){
			return i;
		}
		return find_parent(parent, parent[i]);
	}

	private static void union_parent(int[] parent, int src, int dst) {
		int a = find_parent(parent, src);
		int b = find_parent(parent, dst);

		if (a == b){ // cycle
			parent[a] = 0;
		}else if (a < b){
			parent[b] = a;
		}else{
			parent[a] = b;
		}
	}
}
