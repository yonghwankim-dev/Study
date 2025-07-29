package BOJ.BOJ_10820;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void solution(String str)
	{
		int[] arr;
		StringBuilder answer = new StringBuilder();
		
		arr = new int[4];
		answer = new StringBuilder();
		for(int i=0;i<str.length();i++)
		{
			
			if(Character.isLowerCase(str.charAt(i))) 
			{
				arr[0]++;
			}
			else if(Character.isUpperCase(str.charAt(i)))
			{
				arr[1]++;
			}
			else if(Character.isDigit(str.charAt(i)))
			{
				arr[2]++;
			}
			else if(Character.isSpaceChar(str.charAt(i)))
			{
				arr[3]++;
			}
		}
		for(int i=0;i<arr.length;i++)
		{
			answer.append(arr[i]+" ");
		}
		System.out.println(answer.toString().trim());
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		List<String> sen = new ArrayList<String>();
		
		String str = "";
		while((str=br.readLine())!=null)
		{
			solution(str);
		}
	}
}
