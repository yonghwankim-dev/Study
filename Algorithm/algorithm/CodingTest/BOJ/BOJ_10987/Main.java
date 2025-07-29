package BOJ.BOJ_10987;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Main {
	
	public static int solution(String str)
	{
		int answer = 0;
		Map<Character, Character> map = new HashMap<>();
		map.put('a', 'a');
		map.put('e', 'e');
		map.put('i', 'i');
		map.put('o', 'o');
		map.put('u', 'u');
		
		
		for(int i=0;i<str.length();i++)
		{
			if(map.containsKey(str.charAt(i)))
			{
				answer++;
			}
		}
		return answer;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String str = br.readLine();
		
		System.out.println(solution(str));
		
	}
}
