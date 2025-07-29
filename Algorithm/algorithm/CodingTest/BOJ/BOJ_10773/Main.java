package BOJ.BOJ_10773;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<Integer> list = new ArrayList<Integer>();
		
		int tc = Integer.parseInt(br.readLine());
		for(int i=0;i<tc;i++)
		{
			int input = Integer.parseInt(br.readLine());
			if(input==0 && list.size()!=0)
			{
				list.remove(list.size()-1);
			}
			else
			{
				list.add(input);
			}	
		}
		int sum = 0;
		for(Integer a : list)
		{
			sum += a;
		}
		System.out.println(sum);
		
	}
}
