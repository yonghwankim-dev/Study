package BOJ.BOJ_1966_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;




public class Main {
	static class Node{
		int priority;	// �߿䵵
		int id;			// ���� id
		
		public Node(int priority, int id) {
			this.priority = priority;
			this.id = id;
		}

		@Override
		public String toString() {
			return "Node [priority=" + priority + ", id=" + id + "]";
		}

	}

	public static int solution(List<Node> printer, PriorityQueue<Integer> prioritys, int target)
	{
		int count=0;
		while(!printer.isEmpty())
		{
			Node node = printer.remove(0);
			
			// printer ť�� �� �տ� �ִ� ������ �켱������ prioritys �켱����ť�� ���� �տ� �ִ� �켱������ ������ �˻�
			if(node.priority==prioritys.peek())
			{
				prioritys.poll();
				count++;
				if(node.id==target)
				{
					break;
				}
			}
			else
			{
				printer.add(node);
			}
		}
		return count;
	}
	
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int tc = Integer.parseInt(br.readLine());		// �׽�Ʈ���̽� ����
		
		for(int t=0;t<tc;t++)
		{
			String[] str = br.readLine().split(" ");
			int N = Integer.parseInt(str[0]);		// ���� ����
			int target = Integer.parseInt(str[1]);	// ���°�� �μ��ϴ��� �ñ��� ����id
			
			str = br.readLine().split(" ");
			
			List<Node> printer = new ArrayList<>();
			PriorityQueue<Integer> prioritys = new PriorityQueue<Integer>(Collections.reverseOrder());
			
			
			for(int i=0;i<N;i++)
			{	
				printer.add(new Node(Integer.parseInt(str[i]),i));
				prioritys.add(Integer.parseInt(str[i]));
			}
			System.out.println(solution(printer, prioritys, target));
			
		}
	}
}


