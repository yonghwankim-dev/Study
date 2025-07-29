package PROM.PROM_84512;

import java.util.ArrayList;
import java.util.stream.IntStream;

class Solution {
	private static String[] vowels = {"", "A", "E", "I", "O", "U"};
	private static String[] permutation;
	private static int n, r;
	private static ArrayList<String> list;

	public static int solution(String word){
		list = new ArrayList<>();
		n = 5;
		permutation = new String[n + 1];
		for(int i = 1; i <= n; i++){
			dfs(1);
		}
		return list.indexOf(word) + 1;
	}

	private static void dfs(int depth){
		if(depth == n + 1){
			store(depth);
			return;
		}

		for(int i = 0; i <= n; i++){
			if(i == 0){
				store(depth);
				continue;
			}
			permutation[depth] = vowels[i];
			dfs(depth + 1);
		}
	}

	private static void store(int depth){
		StringBuilder sb = new StringBuilder();
		IntStream.range(1, depth).forEach(i->sb.append(permutation[i]));
		if(sb.length() != 0){
			list.add(sb.toString());
		}
	}
}
