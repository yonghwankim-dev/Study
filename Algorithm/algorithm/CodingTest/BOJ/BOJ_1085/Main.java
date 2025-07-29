package BOJ.BOJ_1085;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	
	public static int solution(int x, int y, int w, int h)
	{
		return Math.min(Math.min(Math.min(w-x, x),h-y),y);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		int x = Integer.parseInt(str[0]);
		int y = Integer.parseInt(str[1]);
		int w = Integer.parseInt(str[2]);
		int h = Integer.parseInt(str[3]);
		System.out.println(solution(x, y, w, h));
	}
}
