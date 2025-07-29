package graph.graph02_bfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 인접리스트 기반 무방향 그래프의 BFS순회
public class UndirectedGraph implements Graph{
	private int V;	// 노드들의 번호
	private ArrayList<ArrayList<Integer>> adj;	// 인접리스트
	
	public UndirectedGraph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(v);
		
		for(int i=0;i<v;i++)
		{
			adj.add(new ArrayList<Integer>());
		}	
	}
	
	@Override
	public void bfsAll() {
		boolean[] visited = new boolean[V];
		
		for(int i=0;i<V;i++)
		{
			if(!visited[i])
			{
				System.out.printf("%d번 노드에서 시작하는 BFS 순회\n",i);
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
	public void addEdge(int u, int v) {
		adj.get(u).add(v);
		adj.get(v).add(u);
	}
	
	@Override
	public void printGraph()
	{
		for(int i=0;i<adj.size();i++)
		{
			System.out.printf("노드 %d의 인접리스트\n", i);
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
		UndirectedGraph undirectedGraph = new UndirectedGraph(V);
		
		undirectedGraph.addEdge(1,2);
		undirectedGraph.addEdge(1,3);
		undirectedGraph.addEdge(2,3);
		undirectedGraph.addEdge(2,4);
		undirectedGraph.addEdge(2,5);
		undirectedGraph.addEdge(3,5);
		undirectedGraph.addEdge(3,7);
		undirectedGraph.addEdge(3,8);
		undirectedGraph.addEdge(4,5);
		undirectedGraph.addEdge(5,6);
				
        undirectedGraph.bfsAll();
	}
}
