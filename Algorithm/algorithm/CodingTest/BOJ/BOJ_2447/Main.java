package BOJ.BOJ_2447;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void print()
	{
		System.out.println("***");
		System.out.println("* *");
		System.out.println("***");
	}
	
	public static int recursion(int n)
	{
		if(n<=3)
		{
			print();
			return 0;
		}
		print();
		return recursion(n/3);
	}
	
	public static void solution(int n)
	{
		recursion(n);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		solution(n);
	}
}
