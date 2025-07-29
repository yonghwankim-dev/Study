package BOJ.BOJ_2480;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static int solution(int[] diceCnt)
	{
		int answer = 0;
		int maxIdx = 0;
		int maxVal = 0;
		for(int i=1;i<diceCnt.length;i++)
		{
			if(diceCnt[i]>=maxVal)
			{
				maxIdx = i;
				maxVal = diceCnt[i];
			}
		}
		
		if(maxVal==1)
		{
			answer = maxIdx*100;
		}
		else if(maxVal==2)
		{
			answer = 1000 + (maxIdx*100);
		}
		else if(maxVal==3)
		{
			answer = 10000 + (maxIdx*1000);
		}
		
		return answer; 
	}

	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str[] = br.readLine().split(" ");
		int[] diceCnt = new int[7];
		
		
		for(int i=0;i<3;i++)
		{
			int n = Integer.parseInt(str[i]);
			diceCnt[n]++;
		}
		System.out.println(solution(diceCnt));
	}
}
