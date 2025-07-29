package BOJ.BOJ_1212;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	private static String[] map = {"000","001","010","011","100","101","110","111"};
	
	/* 8������ �Է¹����� 2������ ��ȯ�����ش�.*/
	public static String solution(String n)
	{
		StringBuilder answer = new StringBuilder();
		
		/* ���� 0�� ���*/
		if(n.equals("0"))
		{
			return "0";
		}
		
		/* 8������ ���� ���ں��� �����Ͽ� 2������ �����Ѵ�.*/
		for(int i=0;i<n.length(); i++)
		{
			answer.append(map[Integer.parseInt(String.valueOf(n.charAt(i)))]);
		}
		
		/* 2������ �� ���ڸ��� 0���� ���� */
		while(answer.charAt(0)=='0')
		{
			answer = answer.deleteCharAt(0);
		}
		
		
		return answer.toString();
		
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println(solution(br.readLine()));
		
	}
}
