package BOJ.BOJ_1330;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int A,B;
		String answer = "";
		String str[] = br.readLine().split(" ");
		A = Integer.parseInt(str[0]);
		B = Integer.parseInt(str[1]);
		
		if(A>B)
		{
			answer = ">";
		}else if(A<B)
		{
			answer = "<";
		}else
		{
			answer = "==";
		}
		System.out.print(answer);
	}
}
