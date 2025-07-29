package BOJ.BOJ_9663;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main2 {
	static int count = 0;
	
	/**
	 * 유망성 조건
	 * 1. 같은 행이 아님
	 * 2. 대각선이 아님 (가로 차이와 세로 차이가 같으면 대각선)
	 * 
	 * @param q 퀸들이 몇번째 행에 저장되는지 저장하는 배열
	 * @param n 유망성이 있는지 검사하는 n번째 퀸
	 * @return
	 */
	public static boolean isPromising(int[] q, int n)
	{
		for(int i = 0; i < n; i++)
		{
			// q[i]==q[n] : 같은 행
			// q[i]-q[n] : 행의 차이
			// n-i : 열의 차이
			if(q[i]==q[n] || Math.abs((q[i]-q[n]))==Math.abs(n-i))
			{
				return false;
			}	
		}
		return true;
	}
	
	public static void enumerate(int N) {
		int[] q = new int[N];	// Queen 배열, index는 열, 값은 행을 의미함
								// ex) q[0]=1, 0번째 퀸은 0번째 열에 1행에 배치
		enumerate(q, 0);
	}
	
	public static void enumerate(int[] q, int n){
		int N = q.length;
		
		// n이 끝가지 돌았다면 탐색완료이므로 count를 증가시킴
		if(n==N) 
		{
			count++;
		}
		else
		{
			for(int i = 0; i < N; i++)
			{
				q[n] = i;				// n번째 퀸을 i번째 행에 배치
				if(isPromising(q,n))	// 유망하다면 계속 탐색	
				{
					enumerate(q, n+1);
				}
			}
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		
		enumerate(n);
		System.out.println(count);
		
	}
}
