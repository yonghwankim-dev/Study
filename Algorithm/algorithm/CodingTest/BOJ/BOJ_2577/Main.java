package BOJ.BOJ_2577;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void solution1() throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] answer = new int[10];
		int sum=1;
		for(int i=0;i<3;i++)
		{
			sum *= Integer.parseInt(br.readLine());
		}
		
		String str = Integer.toString(sum);
		for(Byte value : str.getBytes())
		{
			answer[value-'0']++;
		}
		for(int v : answer)
		{
			System.out.println(v);
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		solution1();
	}
	
}
