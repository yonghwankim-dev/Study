package graph.graph07_disjointset_pathcompression;

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
	public int find(Subset[] subsets, int i)
	{
		if(subsets[i].parent!=i)
		{
			subsets[i].parent = find(subsets, subsets[i].parent);
		}
		return subsets[i].parent;
	}
	
	@Override
	public void union(Subset[] subsets, int x, int y)
	{
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);
		
		if(subsets[xroot].rank < subsets[yroot].rank)
		{
			subsets[xroot].parent = yroot;
		}
		else if(subsets[xroot].rank > subsets[yroot].rank)
		{
			subsets[yroot].parent = xroot;
		}
		else
		{
			subsets[xroot].parent = yroot;
			subsets[yroot].rank++;
		}	
	}
	
	@Override
	public boolean isCycle()
	{
		Subset[] subsets = new Subset[V];
		for(int v=0;v<V;v++)
		{
			subsets[v] = new Subset();
			subsets[v].parent = v;
			subsets[v].rank = 1;
		}
		
		for (int e = 0; e < edges.size(); e++) {
            int x = find(subsets, edges.get(e).src);
            int y = find(subsets, edges.get(e).dest);
            if (x == y)
            {
            	return true;
            }
            union(subsets, x, y);
        }
		return false;
	}
}

