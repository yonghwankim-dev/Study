package BOJ.BOJ_1032;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	
	/* files���� ���� Ž��*/
	public static String solution(List<String> files)
	{
		/* �ʱ� ���� ���ڿ� */
		String pattern = files.get(0);
		
		/* ������ ���ϵ�� pattern�� ���Ͽ� ���� ������ �����Ѵ�. */
		for(int i=1; i<files.size(); i++)
		{
			String dummy = "";
			/* �ϳ��� ���ϰ� ������ ���ڵ��� �ϳ��� ���Ͽ� ���ڰ� Ʋ�� ��� �ش� �ڸ��� ?�� ä���.*/
			for(int j=0; j<files.get(i).length(); j++)
			{
				if(files.get(i).charAt(j)==pattern.charAt(j))
				{
					dummy += pattern.charAt(j);
				}
				else
				{
					dummy += "?";
				}
			}
			pattern = dummy;
		}
		return pattern;
	}
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		List<String> files = new ArrayList<>();
		
		for(int i=0;i<n;i++)
		{
			files.add(br.readLine());
		}
		String pattern = solution(files);
		System.out.println(pattern);
	}
}
