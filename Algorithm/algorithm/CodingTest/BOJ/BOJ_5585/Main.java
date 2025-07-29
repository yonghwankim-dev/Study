package BOJ.BOJ_5585;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		int penny = 1000 - Integer.parseInt(br.readLine());
		int penny_ = penny;
		
		int count = 0;
		int[] moneys = {500,100,50,10,5,1};
		for(Integer money : moneys) 
		{
			count += (penny_ / money);
			penny_ = penny_ % money;
			
			if(penny_==0)
			{
				break;
			}
		}
		System.out.println(count);
		
	}
}
