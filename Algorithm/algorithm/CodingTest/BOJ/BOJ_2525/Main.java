package BOJ.BOJ_2525;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int[] solution(int hour, int min, int cook_time) {
		int[] answer = new int[2];
		
		// 1000 : 16h 40m
		
		int m = hour*60 + min + cook_time;
		int h;
		
		h = m/60>=24 ? (m/60)-24 : m/60;
		m = m%60;
		
		answer[0] = h;
		answer[1] = m;
		
		return answer;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		int hour = Integer.parseInt(str[0]);
		int min = Integer.parseInt(str[1]);
		
		int cook_time = Integer.parseInt(br.readLine());
		int[] answer = solution(hour, min, cook_time);
		System.out.println(answer[0] + " " + answer[1]);
	}
}
