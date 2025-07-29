package BOJ.BOJ_3053;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static final double PI = 3.14159265359;
	
	public static void solution(int r)
	{
		System.out.println(String.format("%.6f", r*r*PI));
		System.out.println(String.format("%.6f", 2.0*r*r));
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int r = Integer.parseInt(br.readLine());
		solution(r);
	}
}
