package BOJ.BOJ_1043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		int truthPerson = Integer.parseInt(st.nextToken());
		List<Integer> truthPersonList = new ArrayList<>();
		for (int i = 0; i < truthPerson; i++){
			truthPersonList.add(Integer.parseInt(st.nextToken()));
		}

		List<Integer>[] personList = new ArrayList[m];
		for (int i = 0; i < m; i++){
			st = new StringTokenizer(br.readLine());
			personList[i] = new ArrayList<>();
			int person = Integer.parseInt(st.nextToken());
			for (int j = 0; j < person; i++){
				personList[i].add(Integer.parseInt(st.nextToken()));
			}
		}
		int answer = solution(truthPersonList, personList);
		System.out.println(answer);
	}

	public static int solution(List<Integer> truthPersonList, List<Integer>[] personList) {

		return 0;
	}
}
