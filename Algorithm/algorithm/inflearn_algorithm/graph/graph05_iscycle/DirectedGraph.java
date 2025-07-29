package graph.graph05_iscycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class DirectedGraph<E> implements Graph<E>{
	private int V; // 노드 개수
	private ArrayList<ArrayList<Node<E>>> adj;	// 인접리스트
	private Map<Integer,E> map;
	
	public DirectedGraph(int v) {
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
		boolean[] recStack = new boolean[V];
		
		for(int i=0;i<V;i++)
		{
			if(isCyclicUtil(i, visited, recStack))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isCyclicUtil(int i, boolean[] visited, boolean[] recStack)
	{
		if(recStack[i])
		{
			return true;
		}
		
		if(visited[i])	
		{
			return false;
		}
		
		visited[i] = true;	
		recStack[i] = true;	
		
		Iterator<Node<E>> itor = adj.get(i).iterator();
		
		while(itor.hasNext())
		{
			int adjNode = itor.next().getNum();
			if(isCyclicUtil(adjNode, visited, recStack))
			{
				return true;
			}
		}
		
		recStack[i] = false;
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
	}
	
	public static void main(String[] args)
	{
		int V = 4;
		DirectedGraph<Integer> directedGraph = new DirectedGraph<Integer>(V);
		
		Node<Integer> node0 = new Node<Integer>(0, 0);
		Node<Integer> node1 = new Node<Integer>(1, 1);
		Node<Integer> node2 = new Node<Integer>(2, 2);
		Node<Integer> node3 = new Node<Integer>(3, 3);
		
		directedGraph.addEdge(node0, node1);
		directedGraph.addEdge(node0, node2);
		directedGraph.addEdge(node1, node2);
		directedGraph.addEdge(node2, node0);
		directedGraph.addEdge(node2, node3);
		
		if(directedGraph.isCyclic())
		{
			System.out.println("이 그래프는 싸이클이 존재합니다.");
		}
		else
		{
			System.out.println("이 그래프는 싸이클이 존재하지 않습니다.");
		}
		
		           
	}
}
