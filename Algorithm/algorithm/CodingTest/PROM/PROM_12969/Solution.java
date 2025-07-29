package PROM.PROM_12969;

import java.util.Scanner;

public class Solution{

	public static String solution(int n, int m)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				sb.append('*');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println(solution(a,b));
    }
}
