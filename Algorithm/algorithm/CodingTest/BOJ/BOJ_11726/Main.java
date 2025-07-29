package BOJ.BOJ_11726;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static long[] arr = new long[1001];
	public static long solution(int n)
	{
		if(n<=2)
		{
			arr[n] = n;
			return arr[n];
		}

		long first = (arr[n-1]!=0 ? arr[n-1] : solution(n-1));
		long second = (arr[n-2]!=0 ? arr[n-2] : solution(n-2));
		
		arr[n] = first + second;
		return arr[n]%10007;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));
		
	}
}
