package BOJ.BOJ_2588;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int A,B;
		int answer[] = new int[4];
		A = Integer.parseInt(br.readLine());
		B = Integer.parseInt(br.readLine());
		
		int B_ = B;
		int i=0;
		while(B_ != 0)
		{
			int nmg = B_%10;
			answer[i] = A*nmg;
			B_ = B_/10;
			i++;
		}
		answer[3] = A*B; 
		
		for(int item : answer)
		{
			System.out.println(item);
		}
	}
}
