package graph.graph09_prim_mst;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UndirectedGraph<E>{
		
	int V;										// ����� ����
	private ArrayList<ArrayList<Node<E>>> adj;	// ��������Ʈ
	private Map<Integer, Node<E>> map;			// ���� ��ȣ�� �ؽø�	
	private int[][] edges;						// ������ ����ġ 

	static class Node<E> implements Comparable<Node<E>>{
		int num;
		E value;
		
		public Node(int num, E value) {
			this.num = num;
			this.value = value;
		}

		@Override
		public int compareTo(Node<E> o) {
			return this.num - o.num;
		}

				
	}
	
	public UndirectedGraph(int v) {
		V = v;
		this.adj = new ArrayList<ArrayList<Node<E>>>(V);
		
		for(int i=0;i<V;i++)
		{
			this.adj.add(new ArrayList<Node<E>>());
			
		}
		
		this.map = new HashMap<Integer, Node<E>>();
		
		this.edges = new int[V][V];
	}

	// ���� �߰�
	public void addEdge(Node<E> u, Node<E> v, int weight) {
		if(!map.containsKey(u.num))
		{
			map.put(u.num, u);
		}
		if(!map.containsKey(v.num))
		{
			map.put(v.num, v);
		}
		adj.get(u.num).add(v);
		adj.get(v.num).add(u);

		edges[u.num][v.num] = weight;
		edges[v.num][u.num] = weight;
	}
	
	// O(n^2)
	public void primMST1(int start)
	{
		// MST Ʈ���� ���� ���� �ڽ��� �����ϴ� ������ ��
		// ����ġ�� �ּ��� ���� (u,v)�� ����ġ�� �ǹ���.
		int[] key = new int[V];
		// �ּ� ����ġ�� ���� (u,v)�� ���� u�� �ǹ���.
		int[] parent = new int[V];
		
		// ��� ����� key���� parent���� �ʱ�ȭ
		for(int u=0; u<V; u++)
		{
			key[u] = Integer.MAX_VALUE;
			parent[u] = u; 
		}
		
		// MST ���տ� ���Ե� ���� 
		ArrayList<Node<E>> nodes = new ArrayList<Node<E>>();
		
		// start ��忡������ ������
		// ���� ���� ����ġ�� ���� ������ 0���� ������
		key[start] = 0;
		int v = 0;
		while(nodes.size()<V)	// MST ������ ������ V���� �ɶ����� �ݺ���
		{
			// MST ���տ� ������ �����鼭 �ּ� ����ġ�� ������ ��� Ž��
			int u = findMinKey(nodes,key);
			
			// MST ���տ� �߰�
			nodes.add(map.get(u));
			
			// u�� ��������� key���� parent�� ����
			for(Node<E> node : adj.get(u))
			{
				v = node.num;
				// ���� ��� v���� MST ���տ� ���Ե��� �����鼭 ���� ����ġ�� ���� ���� ������ ���� 
				if(key[v] > edges[u][v] && !nodes.contains(map.get(v)))
				{
					key[v] = edges[u][v];
					parent[v] = u;
				}
			}
		}
	
		// MST ��� ���
		printMST(key, parent);
	}
	
	public void primMST2(int start)
	{
		// MST Ʈ���� ���� ���� �ڽ��� �����ϴ� ������ ��
		// ����ġ�� �ּ��� ���� (u,v)�� ����ġ�� �ǹ���.
		int[] key = new int[V];
		// �ּ� ����ġ�� ���� (u,v)�� ���� u�� �ǹ���.
		int[] parent = new int[V];
		
		// ��� ����� key���� parent���� �ʱ�ȭ
		for(int u=0; u<V; u++)
		{
			key[u] = Integer.MAX_VALUE;
			parent[u] = u; 
		}
		 
		PriorityQueue<Node<E>> nodes = new PriorityQueue<Node<E>>();
		for(Node<E> node : map.values())
		{
			nodes.add(node);
		}
		
		// start ��忡������ ������
		// ���� ���� ����ġ�� ���� ������ 0���� ������
		key[start] = 0;
		int v = 0;
		while(!nodes.isEmpty())	// MST ������ ������ V���� �ɶ����� �ݺ���
		{
			int u = nodes.poll().num;
			
			// u�� ��������� key���� parent�� ����
			for(Node<E> node : adj.get(u))
			{
				v = node.num;
				
				if(key[v] > edges[u][v] && nodes.contains(map.get(v)))
				{
					key[v] = edges[u][v];
					parent[v] = u;
				}
			}
		}
	
		// MST ��� ���
		printMST(key, parent);
	}
	
	public int findMinKey(ArrayList<Node<E>> nodes, int[] key)
	{
		int min_idx = 0;	// �ּ� ����ġ�� ������ ��� �ε���
		int min_val = Integer.MAX_VALUE;	// �ּ� ����ġ ��
		
		for(int i=0;i<V;i++)
		{
			if(key[i]<min_val && !nodes.contains(map.get(i)))
			{
				min_idx = i;
				min_val = key[i];
			}
		}
		return min_idx;
	}
	
	public void printMST(int[] key, int[] parent)
	{
		System.out.println("key : " + Arrays.toString(key));
		System.out.println("parent : " + Arrays.toString(parent));
		System.out.println("MST Edges");
		for(int i=1;i<V;i++)
		{
			System.out.println(map.get(parent[i]).value + " - " + map.get(i).value + "\t" + edges[i][parent[i]]);
		}
	}
	
	public void printGraph()
	{
		System.out.println("�׷����� ��� ����");
		for(int i=0; i<V; i++)
		{
			ArrayList<Node<E>> list = adj.get(i);
			System.out.print(map.get(i).value+"->");
			for(Node<E> node : list)
			{
				System.out.print(node.value + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("�׷��� ���� ����");
		for(int u=0;u<V;u++)
		{
			for(int v=0;v<V;v++)
			{
				System.out.print(edges[u][v] + " ");
			}
			System.out.println();
		}		
	}
}
