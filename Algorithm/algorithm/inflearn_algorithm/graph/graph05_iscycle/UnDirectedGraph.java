package graph.graph05_iscycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UnDirectedGraph<E> implements Graph<E>{
	private int V; // 노드 개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer,E> map;
	
	public UnDirectedGraph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Node<E>>>(v);
		
		for(int i=0;i<v;i++)
		{
			adj.add(new ArrayList<Node<E>>());
		}
		map = new HashMap<Integer, E>();
	}
	
	public boolean isCyclic()
	{
		boolean[] visited = new boolean[V];
		int parent = -1;
		
		for(int i=0;i<V;i++)
		{
			if(!visited[i])
			{
				if(isCyclicUtil(i, visited, parent))
				{
					return true;
				}
			}		
		}
		return false;
	}
	
	private boolean isCyclicUtil(int i, boolean[] visited, int parent)
	{
		visited[i] = true;	
			
		Iterator<Node<E>> itor = adj.get(i).iterator();
		
		while(itor.hasNext())
		{
			int adjNode = itor.next().getNum();
			if(!visited[adjNode])
			{
				if(isCyclicUtil(adjNode, visited, i))
				{
					return true;
				} 
			}
			else if(adjNode!=parent)
			{
				return true;
			}	
		}
		return false;
	}
	
	@Override
	public void addEdge(Node u, Node v) {
		if(!map.containsKey(u.getNum()))
		{
			map.put(u.getNum(), (E) u.getValue());
		}
		if(!map.containsKey(v.getNum()))
		{
			map.put(v.getNum(), (E) v.getValue());
		}
		
		adj.get(u.getNum()).add(v);
		adj.get(v.getNum()).add(u);
	}
	
	public static void main(String[] args)
	{
		int V = 5;
		UnDirectedGraph<Integer> unDirectedGraph = new UnDirectedGraph<Integer>(V);
		
		Node<Integer> node0 = new Node<Integer>(0, 0);
		Node<Integer> node1 = new Node<Integer>(1, 1);
		Node<Integer> node2 = new Node<Integer>(2, 2);
		Node<Integer> node3 = new Node<Integer>(3, 3);
		Node<Integer> node4 = new Node<Integer>(4, 4);
		
		unDirectedGraph.addEdge(node0, node1);
		unDirectedGraph.addEdge(node0, node2);
		unDirectedGraph.addEdge(node0, node3);
		unDirectedGraph.addEdge(node1, node2);
		unDirectedGraph.addEdge(node3, node4);
		
		
		if(unDirectedGraph.isCyclic())
		{
			System.out.println("이 그래프는 싸이클이 존재합니다.");
		}
		else
		{
			System.out.println("이 그래프는 싸이클이 존재하지 않습니다.");
		}
		
		           
	}
}
