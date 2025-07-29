package BOJ.BOJ_5086;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
		while(true)
		{
			String[] str = br.readLine().split(" ");
			int a = Integer.parseInt(str[0]);
			int b = Integer.parseInt(str[1]);
			
			if(a==0 && b==0)
			{
				break;
			}
			
			if(b%a==0)
			{
				System.out.println("factor");
			}
			else if(a%b==0)
			{
				System.out.println("multiple");
			}
			else
			{
				System.out.println("neither");
			}
		}
		
		
	}
}
