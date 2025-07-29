package BOJ.BOJ_14681;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int x,y;
		int answer = 0;

		x = Integer.parseInt(br.readLine());
		y = Integer.parseInt(br.readLine());
		
		if(x>0)
		{
			if(y>0)
			{
				answer = 1;
			}else if(y<0){
				answer = 4;
			}
		}else if(x<0)
		{
			if(y>0)
			{
				answer = 2;
			}else if(y<0){
				answer = 3;
			}	
		}
		
		System.out.println(answer);
	}
}
