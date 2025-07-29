package BOJ.BOJ_11720;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void solution1() throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String str = br.readLine();
		int sum=0;
		for(int i=0;i<str.length();i++)
		{
			sum += Integer.parseInt(str.charAt(i)+"");
		}
		System.out.println(sum);
	}
	
	public static void solution2() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine(); // N�� �ʿ䰡 ����
		
		int sum = 0;
		
		for(byte value : br.readLine().getBytes())
		{
//			System.out.println(value); // byte value => '0' -> 48
			sum += (value-'0');
		}
		System.out.println(sum);
	}
	
	public static void main(String args[]) throws IOException
	{
		solution2();
	}
	
}
