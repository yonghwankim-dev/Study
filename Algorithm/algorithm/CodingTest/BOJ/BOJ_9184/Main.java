package BOJ.BOJ_9184;
/**
 * ���� ��ȹ�� �ַ��
 * step1 ��ȭ�� ã�� (������ȹ���� �ٽ��� �ݺ��Ǵ� ����� ���̴� ���̱� ������ ��κ� ��ȭ������ ǥ�� ����)
 * step2 ��ȭ�Ŀ��� �ݺ��Ǵ� ���� Ȯ���ϱ�
 * step3 ��ȭ���� ��ͽ����� ��ȯ, �ݺ��Ǵ� ������ �޸𸮿� �����ϱ�
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static int[][][] arr = new int[21][21][21];
	public static int w(int a, int b, int c)
	{
		if(a>=0 && a<=20 && b>=0 && b<=20 && c>=0 && c<=20)
		{
			if(arr[a][b][c]!=0)
			{
				return arr[a][b][c];
			}
		}
		
		if(a<=0 || b<=0 || c<=0)
		{
			return 1;
		}
		
		// a,b,c �� �ϳ��� 20���� ũ�� a,b,c ���� 20���� ������
		if(a>20 || b>20 || c>20)
		{	
			return arr[20][20][20] = w(20,20,20);
		}
		
		if(a<b && b<c)
		{
			return arr[a][b][c] = w(a,b,c-1) + w(a,b-1,c-1) - w(a,b-1,c);
		}
		
		return arr[a][b][c] = w(a-1,b,c) + w(a-1,b-1,c) + w(a-1,b,c-1) - w(a-1,b-1,c-1);
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while(true)
		{
			String str[] = br.readLine().split(" ");
			int a = Integer.parseInt(str[0]);
			int b = Integer.parseInt(str[1]);
			int c = Integer.parseInt(str[2]);
			
			if(a==-1 && b==-1 && c==-1)
			{
				break;
			}
			
			int answer = w(a, b, c);
			System.out.printf("w(%d, %d, %d) = %d\n", a, b, c, answer);
		}
		
		
		
	}
}
