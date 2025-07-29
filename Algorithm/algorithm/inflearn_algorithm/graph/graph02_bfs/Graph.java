package graph.graph02_bfs;

import java.util.ArrayList;

public interface Graph {
	
	public void addEdge(int u, int v);
	public void printGraph();
	public void bfsAll();
	public void bfs(int s, boolean[] visited);
}
