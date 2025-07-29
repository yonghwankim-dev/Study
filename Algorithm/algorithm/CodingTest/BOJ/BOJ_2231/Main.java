package BOJ.BOJ_2231;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {

	// n의 분해합 계산
	// n의 부분합 공식 : n과 n을 이루는 각 자리수의 합
	// ex) n=216 => 216+2+1+6 = 223
	// ex) n=9 => 9 = 9
	public static int cal(int n)
	{
		List<Integer> result = new ArrayList<Integer>();	
		
		result.add(n);
		while(n!=0)
		{
			result.add(n%10);
			n/=10;
		}
		
		return result.stream().mapToInt(Integer::intValue).sum();
	}
	
	public static int solution(int n)
	{
		for(int i=1;i<=n;i++)
		{
			int val = cal(i);	// i에 대한 분해합 계산
			if(val==n)
			{
				return i;
			}
		}	
		return 0;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		System.out.println(solution(n));		
	}
}
