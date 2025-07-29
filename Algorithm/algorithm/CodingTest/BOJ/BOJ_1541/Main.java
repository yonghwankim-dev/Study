package BOJ.BOJ_1541;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String exp = br.readLine();
		StringTokenizer st = new StringTokenizer(exp,"-");
		int sum = Integer.MAX_VALUE;
		while(st.hasMoreTokens())
		{
			int temp = 0;
			StringTokenizer add = new StringTokenizer(st.nextToken(),"+");	// �������� ���� ��ū�� �������� �и��Ͽ� �ش� ��ū���� ���Ѵ�.
			
			while(add.hasMoreTokens())
			{
				temp += Integer.parseInt(add.nextToken());
			}
			
			// ù��° ��ū�ΰ�� temp���� ù��° ���� �ȴ�.
			if(sum==Integer.MAX_VALUE)
			{
				sum = temp;
			}
			else
			{
				sum -= temp;
			}
		}
		System.out.println(sum);
	}
}
