package graph.graph03_dfs;

import java.util.ArrayList;

public interface Graph {
	
	public void addEdge(int u, int v);
	public void printGraph();
	public void bfsAll();
	public void bfs(int s, boolean[] visited);
	public void dfsAll();
	public void dfs(int s, boolean[] visited);
}
