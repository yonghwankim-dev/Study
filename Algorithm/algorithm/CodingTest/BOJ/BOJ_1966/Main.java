package BOJ.BOJ_1966;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



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
	
	public static int solution(List<Node> printer, int target)
	{
		int count=0;
		while(!printer.isEmpty())
		{
			Node node = printer.get(0);
			
			/* node���� ���� �켱������ ���� �ٸ� node�� �ִ��� Ž���Ѵ�. */
			boolean flag = false;
			for(int i=1;i<printer.size();i++)
			{
				if(node.priority<printer.get(i).priority)
				{
					flag = true;
					break;
				}
			}
			/* flag==true�̸� node���� ���� �켱������ ������ �ִ� ���̹Ƿ� ����Ʈ�� �ڷ� ������.*/
			printer.remove(0);
			if(flag==true)
			{
				printer.add(node);
			}else
			{
				count++;
				if(node.id==target)
				{
					return count;
				}
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
			int[] nodes = new int[N];
			str = br.readLine().split(" ");
			for(int i=0;i<nodes.length;i++)
			{
				nodes[i] = Integer.parseInt(str[i]);
			}
			
			
			List<Node> printer = new ArrayList<>();
			
			for(int i=0;i<N;i++)
			{	
				printer.add(new Node(nodes[i],i));
			}
			
			
			System.out.println(solution(printer, target));
		}
	}
}
