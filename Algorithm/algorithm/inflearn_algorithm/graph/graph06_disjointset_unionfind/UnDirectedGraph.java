package graph.graph06_disjointset_unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UnDirectedGraph<E> implements Graph<E>{
	private int V; // 노드 개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer,E> map;
	private ArrayList<Edge> edges;
	
	public UnDirectedGraph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Node<E>>>(v);
		for(int i=0;i<v;i++)
		{
			adj.add(new ArrayList<Node<E>>());
		}
		map = new HashMap<Integer, E>();
		edges = new ArrayList<Edge>();	
	}
	
	@Override
	public void addEdge(Node u, Node v) {
		if(!map.containsKey(u.num))
		{
			map.put(u.num, (E) u.value);
		}
		if(!map.containsKey(v.num))
		{
			map.put(v.num, (E) v.value);
		}
		
		adj.get(u.num).add(v);
		adj.get(v.num).add(u);

		edges.add(new Edge(u.num,v.num));
	}

	@Override
	public int find(int[] parent, int i) {
		if(parent[i]==-1)
		{
			return i;
		}
		return find(parent, parent[i]);
	}

	@Override
	public void union(int[] parent, int u, int v) {
		parent[u] = v;
	}

	@Override
	public boolean isCycle() {
		int[] parent = new int[V];
		
		for(int i=0;i<V;i++)
		{
			parent[i] = -1;
		}
		
		for(int i=0;i<edges.size();i++)
		{
			Edge edge = edges.get(i);
			
			int u = find(parent, edge.src);
			int v = find(parent, edge.dest);
			
			if(u==v)
			{
				return true;
			}
			union(parent, u, v);
		}
		return false;
	}	
}

