package graph.graph10_bellmanford_shortestpath;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DirectedGraph<E>{
		
	int V,E;									// V:��尳��, E:��������
	private ArrayList<ArrayList<Node<E>>> adj;	// ��������Ʈ
	private Map<Integer, Node<E>> map;			// ���� ��ȣ�� �ؽø�
	private ArrayList<ArrayList<Edge>> adj_weight;	// ���� ����� ����ġ
	
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
	
	static class Edge{
		int src, dest, weight;
		public Edge(int src, int dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
	}
	
	public DirectedGraph(int V, int E) {
		this.V = V;
		this.E = E;
		this.adj = new ArrayList<ArrayList<Node<E>>>(V);
		
		for(int i=0;i<V;i++)
		{
			this.adj.add(new ArrayList<Node<E>>());
			
		}
		
		this.map = new HashMap<Integer, Node<E>>();
		
		this.adj_weight = new ArrayList<ArrayList<Edge>>(V);
		for(int i=0;i<V;i++)
		{
			adj_weight.add(new ArrayList<Edge>());
		}
	}
	
	public void bellmanFord(int start)
	{
		int[] dist = new int[V];
		int[] predesor = new int[V];
		
		// step1 �ʱ�ȭ
		for(int i=0;i<V;i++)
		{
			dist[i] = Integer.MAX_VALUE;
			predesor[i] = i;
		}
		dist[start] = 0;
		
		// step2 �ִ� ��� ����
		for(int i=0;i<V;i++)
		{	
			for(int e=0;e<V;e++)
			{
				for(Edge edge : adj_weight.get(e))
				{
					int u = edge.src;
					int v = edge.dest;
					int w = edge.weight;
					
					if(dist[u]!=Integer.MAX_VALUE &&
							dist[u] + w < dist[v])
					{
						dist[v] = dist[u] + w;
						predesor[v] = u;
					}
				}
			}	
		}
		
		// step3	���� ����Ŭ�� �ִ��� �˻�
		for(int i=0;i<V;i++)
		{
			for(Edge edge : adj_weight.get(i))
			{
				int u = edge.src; 
				int v = edge.dest;
				int w = edge.weight;
				
				if(dist[u]!=Integer.MAX_VALUE &&
						dist[u] + w < dist[v])
				{
					return;
				}
			}
		}
		
		printDistanceFromSource(dist);
	}
	
	public void printDistanceFromSource(int[] dist)
	{
		System.out.println("Vertex \t\tDistance");
		for(int i=0;i<V;i++)
		{
            System.out.println(map.get(i).value + "\t\t" + dist[i]);
		}
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
		adj_weight.get(u.num).add(new Edge(u.num, v.num, weight));
	}
		
	public void printGraph()
	{
		System.out.println("�׷��� ����");
		for(int i=0; i<V; i++)
		{
			System.out.print(map.get(i).value+"->");
			for(Edge edge : adj_weight.get(i))
			{
				int v = edge.dest;
				int w = edge.weight;
				System.out.printf("%s(%d) ", map.get(v).value,w);
			}
			System.out.println();
		}	
	}
}
