package BOJ.BOJ_31533;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int a = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());

		double m_mock = (double)m / a;
		double n_mock = (double)n / a;
		if (m_mock < n_mock){
			System.out.println(m_mock * 2);
		}else{
			System.out.println(n_mock * 2);
		}
	}
}
