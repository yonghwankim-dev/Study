package BOJ.BOJ_1976;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		int[] parent = new int[n + 1];
		for (int i = 0; i <= n; i++){
			parent[i] = i;
		}

		int[][] map = new int[n + 1][n + 1];
		for (int i = 1; i <= n; i++){
			int[] row = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
			int index = 0;
			for (int j = 1; j <= n; j++){
				map[i][j] = row[index];
				index++;
			}
		}

		for (int i = 1; i <= n; i++){
			for (int j = 1; j <= n; j++){
				if (map[i][j] == 1){
					union_parent(parent, i, j);
				}
			}
		}

		int[] plan = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();

		boolean possible = true;
		int first = find_parent(parent, plan[0]);
		for (int i = 1; i < m; i++){
			int findValue = find_parent(parent, plan[i]);
			if (first != findValue){
				possible = false;
				break;
			}
		}
		if (possible){
			System.out.println("YES");
		}else{
			System.out.println("NO");
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
