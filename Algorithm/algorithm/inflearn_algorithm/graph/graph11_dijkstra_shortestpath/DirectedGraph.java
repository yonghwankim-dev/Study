package graph.graph11_dijkstra_shortestpath;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import graph.graph11_dijkstra_shortestpath.DirectedGraph.Edge;

public class DirectedGraph<E>{
		
	int V,E;									// V:노드개수, E:정점개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer, Node<E>> map;			// 노드와 번호의 해시맵
	private ArrayList<ArrayList<Edge>> adj_weight;	// 인접 노드의 가중치
	
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
			this.adj_weight.add(new ArrayList<Edge>());
		}
	}
	
	// O(n²)
	public void dijkstra(int start)
	{
		int[] dist = new int[V];
		Set<Integer> s = new HashSet<Integer>();
		
		// step1 초기화
		for(int i=0;i<V;i++)
		{
			dist[i] = Integer.MAX_VALUE;
		}
		dist[start] = 0;
		
		while(s.size()<V)
		{
			int u = findMininumKey(s, dist);
			s.add(u);
			
			for(Edge edge : adj_weight.get(u))
			{
				int v = edge.dest;
				int w = edge.weight;
				
				if(dist[v] > dist[u] + w)
				{
					dist[v] = dist[u] + w;
				}
			}
		}
		
		
		printDistanceFromSource(dist);
	}
	
	// O(nlog₂n + mlog₂n) 
	// 이진힙을 우선순위큐로 사용
	public void dijkstra2(int start)
	{
		int[] dist = new int[V];
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>((v1,v2)->v1.weight - v2.weight);
		Set<Integer> s = new HashSet<Integer>();
		
		// step1 초기화
		for(int i=0;i<V;i++)
		{
			dist[i] = Integer.MAX_VALUE;
		}
		dist[start] = 0;
		
		pq.add(new Edge(start, start, 0));
		
		
		while(pq.size()!=0)	// O(n)
		{
			int u = pq.poll().src;	// 힙조정으로 인한 O(log₂n)
			s.add(u);
			
			for(Edge edge : adj_weight.get(u))	// degree(u) = O(m)
			{
				int v = edge.dest;
				int w = edge.weight;
				
				if(!s.contains(v) &&dist[v] > dist[u] + w)
				{
					dist[v] = dist[u] + w;
					pq.add(new Edge(v,v,w));	// 힙조정으로 인한 O(log₂n)
				}
			}
		}
		
		
		printDistanceFromSource(dist);
	}
	
	public int findMininumKey(Set<Integer> s, int[] dist)
	{
		int min_value = Integer.MAX_VALUE;
		int min_idx = -1;
		for(int i=0;i<dist.length;i++)
		{
			if(!s.contains(i) && dist[i] < min_value)
			{
				min_value = dist[i];
				min_idx = i;
			}
		}
		return min_idx;
	}
	
	public void printDistanceFromSource(int[] dist)
	{
		System.out.println("Vertex \t\tDistance");
		for(int i=0;i<V;i++)
		{
            System.out.println(map.get(i).value + "\t\t" + dist[i]);
		}
	}

	// 간선 추가
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
		adj_weight.get(u.num).add(new Edge(u.num,v.num,weight));
	}
		
	public void printGraph()
	{
		System.out.println("그래프 구성");
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
