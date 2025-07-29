package BOJ.BOJ_1157;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;




public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		
		String str = br.readLine();
		
		//step1 �ܾ� �빮��ȭ
		str = str.toUpperCase();
		
		
		
		for(int i=0;i<str.length();i++)
		{
			char ch = str.charAt(i);
			if(map.containsKey(ch))
			{
				map.put(ch, map.get(ch)+1);
			}
			else 
			{
				map.put(ch,1);
			}
		}
		int maxValue = Collections.max(map.values());
		int count = 0;
		char answer = 0;
		// �ִ� ���� ���ĺ� ������ �����Ѱ��� ����� �˻�
		for(Entry<Character,Integer> entry : map.entrySet())
		{
			if(entry.getValue()==maxValue)
			{
				count++;
				answer = entry.getKey();
			}
		}
		
		if(count>=2)
		{
			System.out.println("?");
		}
		else
		{
			System.out.println(answer);
		}
		
		
	}
}
