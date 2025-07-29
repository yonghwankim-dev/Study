package BOJ.BOJ_1717;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int[] parent = new int[n + 1];
		for (int i = 0; i <= n; i++){
			parent[i] = i;
		}

		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			int op = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			if (op == 0){
				union_parent(parent, a, b);
			}else if (op == 1){
				int p1 = find_parent(parent, a);
				int p2 = find_parent(parent, b);
				if (p1 == p2){
					System.out.println("YES");
				}else{
					System.out.println("NO");
				}
			}
		}
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
