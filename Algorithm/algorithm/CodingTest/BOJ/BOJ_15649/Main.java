package BOJ.BOJ_15649;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void solution(int n, int m, List<Integer> p, boolean[] chosen){
		if (p.size() == m){
			p.forEach(num -> System.out.print(num + " "));
			System.out.println();
			return;
		}
		for (int i = 1; i <= n; i++){
			if (chosen[i]){
				continue;
			}
			chosen[i] = true;
			p.add(i);

			solution(n, m, p, chosen);

			chosen[i] = false;
			p.remove(p.size() - 1);
		}
	}


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] inputs = br.readLine().split(" ");
		int n = Integer.parseInt(inputs[0]);
		int m = Integer.parseInt(inputs[1]);
		boolean[] chosen = new boolean[n + 1];
		List<Integer> p = new ArrayList<>();
		solution(n, m, p, chosen);
	}
}
