package BOJ.BOJ_11653;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void solution(int n)
	{
		int n_ = n;
		int divisor = 2;
		while(n_!=1)
		{
			if(n_%divisor==0)
			{
				System.out.println(divisor);
				n_ /= divisor;
			}
			else 
			{
				divisor++;
			}
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		solution(n);
	}
}
