package graph.graph03_dfs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

// ��������Ʈ ��� ���� �׷����� BFS ��ȸ
public class DirectedGraph implements Graph{
	private int V;	// ������ ��ȣ
	private ArrayList<ArrayList<Integer>> adj;	// ��������Ʈ
	
	public DirectedGraph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(v);
		
		for(int i=0;i<v;i++)
		{
			adj.add(new ArrayList<Integer>());
		}	
	}
	
	@Override
	public void dfsAll() {
		boolean[] visited = new boolean[V];
		
		for(int i=0;i<V;i++)
		{
			if(!visited[i])
			{
				System.out.printf("%d�� ��忡�� �����ϴ� DFS ��ȸ\n",i);
				dfs(i,visited);
				System.out.println();
			}
		}
	}


	@Override
	public void dfs(int s, boolean[] visited) {
		visited[s] = true;
		System.out.print(s+" ");
	
		Iterator<Integer> itor = adj.get(s).iterator();
		
		while(itor.hasNext())
		{
			int adjNode = itor.next();
			if(!visited[adjNode])
			{
				dfs(adjNode, visited);
			}
		}
	}
	
	@Override
	public void bfsAll() {
		boolean[] visited = new boolean[V];
		
		for(int i=0;i<V;i++)
		{
			if(!visited[i])
			{
				System.out.printf("%d�� ��忡�� �����ϴ� BFS ��ȸ\n",i);
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
			System.out.print(s+" ");
			
			Iterator<Integer> itor = adj.get(s).listIterator();
			
			while(itor.hasNext())
			{
				int adjNode = itor.next();
				if(!visited[adjNode])
				{
					visited[adjNode] = true;
					queue.add(adjNode);
				}
			}
		}
	}

	@Override
	public void addEdge(int u, int v)
	{
		adj.get(u).add(v);
	}
	
	@Override
	public void printGraph()
	{
		for(int i=0;i<adj.size();i++)
		{
			System.out.printf("��� %d�� ��������Ʈ\n", i);
			System.out.print("head");
			for(int j=0; j<adj.get(i).size();j++)
			{
				System.out.print("->"+adj.get(i).get(j));
			}
			System.out.printf("\n\n");
		}	
	}
	
	public static void main(String[] args)
	{
		int V = 9;
		DirectedGraph directedGraph = new DirectedGraph(V);
		
		directedGraph.addEdge(1,2);
		directedGraph.addEdge(1,3);
		directedGraph.addEdge(2,3);
		directedGraph.addEdge(2,4);
		directedGraph.addEdge(2,5);
		directedGraph.addEdge(3,5);
		directedGraph.addEdge(3,7);
		directedGraph.addEdge(3,8);
		directedGraph.addEdge(4,5);
		directedGraph.addEdge(5,6);
				
		directedGraph.dfsAll();
	}
}
