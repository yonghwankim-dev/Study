package BOJ.BOJ_2884;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int h,m,total;
			
		String str[] = br.readLine().split(" ");
		
		h = Integer.parseInt(str[0]);
		m = Integer.parseInt(str[1]);
		
		total = (h*60 + m)-45; //�Ѻ�
		
		if(total<0)
		{
			total = 24*60 + total;
		}
		h = total/60;
		m = total%60;
		
		System.out.println(h+" " + m);
		
	}
}
