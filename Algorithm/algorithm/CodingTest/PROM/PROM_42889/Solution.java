package PROM.PROM_42889;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Solution {
	
	static class Stage{
		int num;
		double fail_rate;
		
		public Stage(int num, double fail_rate) {
			this.num = num;
			this.fail_rate = fail_rate;
		}

		@Override
		public String toString() {
			return "["+num+"]"+fail_rate;
		}
		
		
		
		
	}
	
	public static int[] solution(int N, int[] stages)
	{
		Arrays.sort(stages);
		Stage[] s = new Stage[N];
		int[] answer = new int[N];
		
		for(int i=1;i<=N;i++)
		{
			int noClearCnt = 0;
			int clearPerson = stages.length;
			for(int j=0;j<stages.length;j++)
			{
				if(stages[j]<i)
				{
					clearPerson--;
				}
				else if(stages[j]==i)
				{
					noClearCnt++;
				}
				else
				{
					break;
				}
			}
			double rate = clearPerson==0 ? 0 : (double) noClearCnt/ (double) clearPerson;
			s[i-1] =  new Stage(i,rate); 
		}
		
		System.out.println(Arrays.toString(s));
		
		Arrays.sort(s,new Comparator<Stage>() {
			@Override
			public int compare(Stage o1, Stage o2) {
				if(o2.fail_rate>o1.fail_rate)
				{
					return 1;
				}
				else if(o2.fail_rate<o1.fail_rate)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		});
		
		for(int i=0;i<s.length;i++)
		{
			answer[i] = s[i].num;
		}
		
		return answer;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] stages = {2, 1, 2, 6, 2, 4, 3, 3};
		System.out.println(Arrays.toString(solution(5, stages)));
	}
}
