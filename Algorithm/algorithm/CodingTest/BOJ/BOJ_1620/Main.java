package BOJ.BOJ_1620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] info = br.readLine().split(" ");
		Map<Integer, String> dogam1 = new HashMap<>(); // key : id, value : name
		Map<String, Integer> dogam2 = new HashMap<>(); // key : name, value : id
		List<String> problems = new ArrayList<>();
		int n = Integer.parseInt(info[0]);
		int m = Integer.parseInt(info[1]);
		String name;

		for(int i = 1; i <= n; i++){
			name = br.readLine();
			dogam1.put(i, name);
			dogam2.put(name, i);
		}

		for(int i = 1; i <= m; i++){
			problems.add(br.readLine());
		}

		String answer = Main.solution(dogam1, dogam2, problems);
		System.out.println(answer);
	}


	public static String solution(Map<Integer,String> dogam1, Map<String, Integer> dogam2, List<String> problems){
		String answer;
		StringBuilder sb = new StringBuilder();
		for(String problem : problems){
			if(isId(problem)){ // id
				answer = dogam1.get(toInt(problem));
				sb.append(answer+"\n");
				continue;
			}
			answer = String.valueOf(dogam2.get(problem)); // name
			sb.append(answer+"\n");
		}
		return sb.toString();
	}

	private static boolean isId(String text){
		try{
			Integer.parseInt(text);
			return true;
		}catch (NumberFormatException e){
			return false;
		}
	}

	private static int toInt(String text){
		return Integer.parseInt(text);
	}
}
