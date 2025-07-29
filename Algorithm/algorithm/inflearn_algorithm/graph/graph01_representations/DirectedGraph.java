package graph.graph01_representations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 인접리스트 기반 방향 그래프
public class DirectedGraph implements Graph{
	private int V;	// 노드들의 번호
	private ArrayList<ArrayList<Integer>> adj;	// 인접리스트
	
	public DirectedGraph(int v) {
		V = v;
		adj = new ArrayList<ArrayList<Integer>>(v);
		
		for(int i=0;i<v;i++)
		{
			adj.add(new ArrayList<Integer>());
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
				
		directedGraph.printGraph();
	}
}
