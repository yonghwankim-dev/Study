package BOJ.BOJ_2839;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int answer = solution(n);
		System.out.println(answer);
	}

	private static int solution(int n) {
		int answer = 0;
		while (true){
			if (n % 5 == 0){
				answer = n / 5 + answer;
				break;
			}else if (n <= 0){
				answer =  -1;
				break;
			}
			n -= 3;
			answer++;
		}
		return answer;
	}
}
