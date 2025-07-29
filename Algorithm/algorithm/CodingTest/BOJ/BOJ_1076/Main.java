package BOJ.BOJ_1076;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	private enum electrical {black, brown, red, orange, yellow, green, blue, violet, grey, white};
	
	public static long solution(List<String> list)
	{
		String value = "";
		long answer = 0;
		for(int i=0;i<list.size();i++)
		{
			if(i>=list.size()-1)
			{
				// ������ ������ ���� �� = 10^���ǰ�
				answer = (long) (Integer.parseInt(value)*Math.pow(10, electrical.valueOf(list.get(i)).ordinal())); 
			}
			else
			{
				value += electrical.valueOf(list.get(i)).ordinal();
			}	
		}
		return answer;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<String> list = new ArrayList<>();
		for(int i=0;i<3;i++)
		{
			list.add(br.readLine());
		}
		System.out.println(solution(list));
		
	}
}
