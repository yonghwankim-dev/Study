package BOJ.BOJ_10989;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int MAX_SIZE = 10000;
	
	public static int[] solution(int[] origin, int n)
	{
		int[] count = new int[MAX_SIZE+1];
		int[] reduce = new int[MAX_SIZE+1];
		int[] result = new int[n+1];

		
		// step1 : Counting
		for(int i=1;i<origin.length;i++)
		{
			count[origin[i]]++;
		}
		
		// step2 : Sum
		for(int i=1; i<=MAX_SIZE;i++)
		{
			reduce[i] = reduce[i-1]+count[i];
		}
		
		
		// step3 : origin의 뒤에서부터 순회
		for(int i=n; i>=1; i--)
		{
			result[reduce[origin[i]]] = origin[i];
			reduce[origin[i]]--;
		}
		
		return result;
	}
	
	public static void main(String args[]) throws IOException
	{
		int n = Integer.parseInt(br.readLine());
		int[] origin = new int[n+1];
		StringBuilder sb = new StringBuilder();
		
		for(int i=1;i<=n;i++)
		{
			origin[i] = Integer.parseInt(br.readLine());
		}
		int[] result = solution(origin, n);
		
		for(int i=1;i<=n;i++)
		{
			sb.append(result[i]+"\n");
		}
		System.out.println(sb.toString());
	}
}
