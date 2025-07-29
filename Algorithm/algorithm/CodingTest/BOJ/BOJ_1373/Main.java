package BOJ.BOJ_1373;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Main {
	private static Map<String, String> map = new HashMap<String, String>(){
		{
			put("000", "0");
			put("001", "1");
			put("010", "2");
			put("011", "3");
			put("100", "4");
			put("101", "5");
			put("110", "6");
			put("111", "7");
		}
	};

	public static String solution(String n)
	{
		StringBuilder answer = new StringBuilder();
	
		for(int i=n.length()-1;i>=0;i-=3)
		{
			if(i-2<0)
			{
				String temp = n.substring(0, i+1);
				while(temp.length()!=3)
				{
					temp = "0" + temp;
				}
				
				answer.append(map.get(temp));
			}
			else
			{
				answer.append(map.get(n.substring(i-2, i+1)));
			}
		}
		
		return answer.reverse().toString();
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println(solution(br.readLine()));
		
	}
}
