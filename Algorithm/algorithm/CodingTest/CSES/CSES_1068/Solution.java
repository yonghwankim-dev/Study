package CSES.CSES_1068;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
	public static void solution(long n)
	{
		StringBuilder sb = new StringBuilder();
		while(true) {
			sb.append(n + " ");
			if(n == 1) {
				break;
			}
			else if(n % 2 == 0) {
				n = n / 2;
			}
			else {
				n = n * 3 + 1;
			}
			
		}
		System.out.println(sb.toString().trim());
	}
	
	public static void main(String args[]) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Integer.parseInt(br.readLine());
		solution(n);
	}
}
