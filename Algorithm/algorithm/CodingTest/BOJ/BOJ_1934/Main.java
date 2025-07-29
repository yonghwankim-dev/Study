package BOJ.BOJ_1934;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static int gcd(int a, int b)
	{
		if(b==0)
		{
			return a;
		}
		else
		{
			return gcd(b, a%b);
		}
	}
	
	
	public static int lcm(int a, int b, int gcd)
	{
		return (a*b)/gcd;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		for(int i=0;i<n;i++)
		{
			String[] str = br.readLine().split(" ");
			int a = Integer.parseInt(str[0]);
			int b = Integer.parseInt(str[1]);
			System.out.println(lcm(a,b,gcd(a,b)));
		}
		
		
	}
}
