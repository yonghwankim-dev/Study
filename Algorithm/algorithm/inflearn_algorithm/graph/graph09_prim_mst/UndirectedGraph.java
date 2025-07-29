package graph.graph09_prim_mst;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class UndirectedGraph<E>{
		
	int V;										// 노드의 개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer, Node<E>> map;			// 노드와 번호의 해시맵	
	private int[][] edges;						// 간선의 가중치 

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
		adj.get(v.num).add(u);

		edges[u.num][v.num] = weight;
		edges[v.num][u.num] = weight;
	}
	
	// O(n^2)
	public void primMST1(int start)
	{
		// MST 트리에 속한 노드와 자신을 연결하는 간선들 중
		// 가중치가 최소인 간선 (u,v)의 가중치를 의미함.
		int[] key = new int[V];
		// 최소 가중치인 간선 (u,v)의 끝점 u를 의미함.
		int[] parent = new int[V];
		
		// 모든 노드의 key값과 parent값을 초기화
		for(int u=0; u<V; u++)
		{
			key[u] = Integer.MAX_VALUE;
			parent[u] = u; 
		}
		
		// MST 집합에 포함된 노드들 
		ArrayList<Node<E>> nodes = new ArrayList<Node<E>>();
		
		// start 노드에서부터 시작함
		// 시작 노드는 가중치가 없기 때문에 0으로 설정함
		key[start] = 0;
		int v = 0;
		while(nodes.size()<V)	// MST 집합의 개수가 V개가 될때까지 반복함
		{
			// MST 집합에 속하지 않으면서 최소 가중치를 가지는 노드 탐색
			int u = findMinKey(nodes,key);
			
			// MST 집합에 추가
			nodes.add(map.get(u));
			
			// u의 인접노드의 key값과 parent값 갱신
			for(Node<E> node : adj.get(u))
			{
				v = node.num;
				// 인접 노드 v에서 MST 집합에 포함되지 않으면서 간선 가중치가 가장 작은 것으로 갱신 
				if(key[v] > edges[u][v] && !nodes.contains(map.get(v)))
				{
					key[v] = edges[u][v];
					parent[v] = u;
				}
			}
		}
	
		// MST 결과 출력
		printMST(key, parent);
	}
	
	public void primMST2(int start)
	{
		// MST 트리에 속한 노드와 자신을 연결하는 간선들 중
		// 가중치가 최소인 간선 (u,v)의 가중치를 의미함.
		int[] key = new int[V];
		// 최소 가중치인 간선 (u,v)의 끝점 u를 의미함.
		int[] parent = new int[V];
		
		// 모든 노드의 key값과 parent값을 초기화
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
		
		// start 노드에서부터 시작함
		// 시작 노드는 가중치가 없기 때문에 0으로 설정함
		key[start] = 0;
		int v = 0;
		while(!nodes.isEmpty())	// MST 집합의 개수가 V개가 될때까지 반복함
		{
			int u = nodes.poll().num;
			
			// u의 인접노드의 key값과 parent값 갱신
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
	
		// MST 결과 출력
		printMST(key, parent);
	}
	
	public int findMinKey(ArrayList<Node<E>> nodes, int[] key)
	{
		int min_idx = 0;	// 최소 가중치를 가지는 노드 인덱스
		int min_val = Integer.MAX_VALUE;	// 최소 가중치 값
		
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
		System.out.println("그래프의 노드 구성");
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
		System.out.println("그래프 간선 구성");
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
