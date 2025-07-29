package BOJ.BOJ_2164;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;


public class Main {
	public static int solution(Queue<Integer> q)
	{
		while(q.size()!=1)	// queue �������� 1���� ���������� �ݺ�
		{
			q.poll();	// ī�������� ����
			q.add(q.poll());	// ���� ���� ī�带 ���� �ڷ� ����
		}
		return q.peek();	// queue ������ ������ 1�� ���� ī���� ���� ��ȯ
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i=1;i<=n;i++)
		{
			q.add(i);
		}
		
		System.out.println(solution(q));
	}
}
