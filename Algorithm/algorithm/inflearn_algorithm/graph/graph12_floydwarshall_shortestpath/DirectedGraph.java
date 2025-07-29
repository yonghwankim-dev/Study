package graph.graph12_floydwarshall_shortestpath;

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

public class DirectedGraph<E>{
	final static int INF = 99999;
	
	int V,E;									// V:노드개수, E:정점개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer, Node<E>> map;			// 노드와 번호의 해시맵
	private int[][] weight;						// 그래프의 가중치
	
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
	
	public DirectedGraph(int V, int E) {
		this.V = V;
		this.E = E;
		this.adj = new ArrayList<ArrayList<Node<E>>>(V);
		
		for(int i=0;i<V;i++)
		{
			this.adj.add(new ArrayList<Node<E>>());
			
		}
		
		this.map = new HashMap<Integer, Node<E>>();
		
		this.weight = new int[V][V];
		for(int i=0;i<V;i++)
		{
			for(int j=0;j<V;j++)
			{
				if(i==j)
				{
					weight[i][j] = 0;
				}
				else
				{
					weight[i][j] = INF;
				}
			}
		}
	}
	
	public void floydWarshall()
	{
		int[][] dist = new int[V][V];
		int[][]	pi = new int[V][V];
		
		for(int i=0;i<V;i++)
		{
			for(int j=0;j<V;j++)
			{
				dist[i][j] = weight[i][j];
				pi[i][j] = -1;
			}
		}
		
		for(int k=0;k<V;k++)
		{
			for(int i=0;i<V;i++)
			{
				for(int j=0;j<V;j++)
				{
					if(dist[i][j] > dist[i][k] + dist[k][j])
					{
						dist[i][j] = dist[i][k] + dist[k][j];
						pi[i][j] = k;
					}
				}
			}
		}
		printDistancePath(dist);
	}
	
	private void printDistancePath(int[][] dist)
	{
		for(int i=0;i<V;i++)
		{
			for(int j=0;j<V;j++)
			{
				if(dist[i][j]==INF)
				{
					System.out.print("INF ");
				}
				else
				{
					System.out.print(dist[i][j] + "   ");
				}
			}
			System.out.println();
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
		this.weight[u.num][v.num] = weight;
	}
		
	public void printGraph()
	{
		System.out.println("그래프 구성");
		for(int i=0; i<V; i++)
		{
			System.out.print(map.get(i).value+"->");
			for(Node<E> node : adj.get(i))
			{
				System.out.printf("%s ", map.get(node.num).value);
			}
			System.out.println();
		}
		
		for(int i=0;i<V;i++)
		{
			for(int j=0;j<V;j++)
			{
				if(weight[i][j]==INF)
				{
					System.out.print("INF ");
				}
				else
				{
					System.out.print(weight[i][j] + "   ");
				}
			}
			System.out.println();
		}
	}
}
