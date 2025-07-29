package graph.graph04_topologicalSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

// 인접리스트 기반 방향 그래프의 BFS 순회
public class DirectedGraph<E> implements Graph{
	private int V;	// 노드들의 번호
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
	
	@Override
	public void topologicalSort() {
		boolean[] visited = new boolean[V];
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int i=1;i<V;i++)
		{
			if(!visited[i])
			{
				topologicalSortUtil(i, visited, stack);
			}
		}
		
		System.out.print("start");
		while(!stack.isEmpty())
		{
			int nodeNum = stack.pop();
			System.out.print("->"+map.get(nodeNum));
		}
		System.out.println();
	}

	@Override
	public void topologicalSortUtil(int v, boolean[] visited, Stack stack) {
		visited[v] = true;
		
		Iterator<Node<E>> itor = adj.get(v).iterator();
		
		while(itor.hasNext())
		{
			int adjNodeNum = itor.next().getNum();
			if(!visited[adjNodeNum])
			{
				topologicalSortUtil(adjNodeNum, visited, stack);
			}
		}
		
		stack.push(v);
	}
		
	@Override
	public void dfsAll() {
		boolean[] visited = new boolean[V];
		
		for(int i=1;i<V;i++)
		{
			if(!visited[i])
			{
				System.out.printf("%d번 노드에서 시작하는 DFS 순회\n",i);
				dfs(i,visited);
				System.out.println();
			}
		}
	}
	
	@Override
	public void dfs(int s, boolean[] visited) {
		visited[s] = true;
		System.out.print(map.get(s)+" ");
	
		Iterator<Node<E>> itor = adj.get(s).iterator();
		
		while(itor.hasNext())
		{
			int adjNodeNum = itor.next().getNum();
			if(!visited[adjNodeNum])
			{
				dfs(adjNodeNum, visited);
			}
		}
		
	}

	
	@Override
	public void bfsAll() {
		boolean[] visited = new boolean[V];
		
		for(int i=1;i<V;i++)
		{
			if(!visited[i])
			{
				System.out.printf("%d번 노드에서 시작하는 BFS 순회\n",map.get(i));
				bfs(i, visited);
				System.out.println();
			}
		}
	}
	
	@Override
	public void bfs(int s, boolean[] visited) {		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		visited[s] = true;
		queue.add(s);
		
		while(queue.size()!=0)
		{
			s = queue.poll();
			System.out.print(map.get(s)+" ");
			
			Iterator<Node<E>> itor = adj.get(s).listIterator();
			
			while(itor.hasNext())
			{
				int adjNodeNum = itor.next().getNum();
				if(!visited[adjNodeNum])
				{
					visited[adjNodeNum] = true;
					queue.add(adjNodeNum);
				}
			}
		}

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

	@Override
	public void printGraph()
	{	
		for(int i=1;i<adj.size();i++)
		{
			System.out.printf("노드 %s의 인접리스트\n", map.get(i));
			System.out.print("head");
			for(int j=0; j<adj.get(i).size();j++)
			{
				Node<E> adjNode = adj.get(i).get(j);
				System.out.print("->"+adjNode.getValue());
			}
			System.out.printf("\n\n");
		}	
	}
	
	public static void main(String[] args)
	{
		int V = 8;
		DirectedGraph<String> directedGraph = new DirectedGraph<String>(V);
		
		Node<String> a = new Node<String>(1, "a");
		Node<String> b = new Node<String>(2, "b");
		Node<String> c = new Node<String>(3, "c");
		Node<String> d = new Node<String>(4, "d");
		Node<String> e = new Node<String>(5, "e");
		Node<String> f = new Node<String>(6, "f");
		Node<String> g = new Node<String>(7, "g");
		
		directedGraph.addEdge(a,b);
		directedGraph.addEdge(a,d);
		directedGraph.addEdge(b,c);
		directedGraph.addEdge(b,e);
		directedGraph.addEdge(d,e);
		directedGraph.addEdge(e,c);
		directedGraph.addEdge(e,f);
		directedGraph.addEdge(g,d);
		
		
		directedGraph.topologicalSort();
           
	}


}
